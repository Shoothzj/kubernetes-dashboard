/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.github.shoothzj.kdash.service.hc;

import com.github.shoothzj.kdash.config.CloudConfig;
import com.github.shoothzj.kdash.config.HuaweiCloudConfig;
import com.huaweicloud.sdk.core.auth.BasicCredentials;
import com.huaweicloud.sdk.ecs.v2.EcsAsyncClient;
import com.huaweicloud.sdk.ecs.v2.model.BatchStopServersOption;
import com.huaweicloud.sdk.ecs.v2.model.BatchStopServersRequest;
import com.huaweicloud.sdk.ecs.v2.model.BatchStopServersRequestBody;
import com.huaweicloud.sdk.ecs.v2.model.BatchStopServersResponse;
import com.huaweicloud.sdk.ecs.v2.model.ChangeServerOsWithoutCloudInitOption;
import com.huaweicloud.sdk.ecs.v2.model.ChangeServerOsWithoutCloudInitRequest;
import com.huaweicloud.sdk.ecs.v2.model.ChangeServerOsWithoutCloudInitRequestBody;
import com.huaweicloud.sdk.ecs.v2.model.ChangeServerOsWithoutCloudInitResponse;
import com.huaweicloud.sdk.ecs.v2.model.ListServersDetailsRequest;
import com.huaweicloud.sdk.ecs.v2.model.ListServersDetailsResponse;
import com.huaweicloud.sdk.ecs.v2.model.ServerId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Slf4j
@Service
public class HcEcsService {
    private final EcsAsyncClient ecsClient;

    public static final int RETRY_PAUSE_TIME = 10000;

    public HcEcsService(@Autowired CloudConfig cloudConfig, @Autowired HuaweiCloudConfig config) {
        ecsClient = EcsAsyncClient.newBuilder()
                .withEndpoint(config.ecsEndpoint)
                .withCredential(new BasicCredentials()
                        .withAk(cloudConfig.accessKey)
                        .withSk(cloudConfig.secretKey)
                        .withProjectId(config.projectId)
                )
                .build();
    }

    public CompletableFuture<ListServersDetailsResponse> getEcsServerList(ListServersDetailsRequest request) {
        return ecsClient.listServersDetailsAsync(request);
    }

    public CompletableFuture<BatchStopServersResponse> stopServers(List<String> serverIds) {
        BatchStopServersRequest request = new BatchStopServersRequest();
        BatchStopServersRequestBody body = new BatchStopServersRequestBody();
        BatchStopServersOption osStop = new BatchStopServersOption();
        List<ServerId> serverIdList = serverIds.stream()
                .map(id -> new ServerId().withId(id)).collect(Collectors.toList());
        osStop.setServers(serverIdList);
        body.setOsStop(osStop);
        request.setBody(body);
        return ecsClient.batchStopServersAsync(request);
    }

    public CompletableFuture<Void> changeos(List<String> serverIds, String imageId) {
        CompletableFuture<Void> future = new CompletableFuture<>();
        // stop servers. this function only send stop command, do not sync server status
        stopServers(serverIds).whenComplete((stopServersResponse, e) -> {
            if (e != null) {
                throw new RuntimeException(e);
            }
            // server status need time to sync
            // changeos
            List<CompletableFuture<ChangeServerOsWithoutCloudInitResponse>> futures = new ArrayList<>();
            for (String serverId : serverIds) {
                ChangeServerOsWithoutCloudInitRequest changeServerOsRequest =
                        new ChangeServerOsWithoutCloudInitRequest();
                changeServerOsRequest.setServerId(serverId);
                ChangeServerOsWithoutCloudInitRequestBody body = new ChangeServerOsWithoutCloudInitRequestBody();
                ChangeServerOsWithoutCloudInitOption option = new ChangeServerOsWithoutCloudInitOption();
                option.setImageid(imageId);
                body.setOsChange(option);
                changeServerOsRequest.setBody(body);
                futures.add(ecsClient.changeServerOsWithoutCloudInitAsync(changeServerOsRequest)
                        .exceptionally(t -> {
                            log.info("it seems that ecs server status not sync yet, let's try again after {}s",
                                    RETRY_PAUSE_TIME / 1000);
                            if (t instanceof CompletionException completionException
                                    && completionException.getMessage().contains("Ecs.0100")) {
                                try {
                                    Thread.sleep(RETRY_PAUSE_TIME);
                                    futures.add(
                                            ecsClient.changeServerOsWithoutCloudInitAsync(changeServerOsRequest));
                                } catch (InterruptedException exc) {
                                    throw new RuntimeException(exc);
                                }
                            }
                            return null;
                        }));
            }
            for (CompletableFuture<ChangeServerOsWithoutCloudInitResponse> responseFuture : futures) {
                try {
                    responseFuture.get();
                } catch (InterruptedException | ExecutionException exception) {
                    throw new RuntimeException(exception);
                }
            }
            future.complete(null);
        });
        return future;
    }
}
