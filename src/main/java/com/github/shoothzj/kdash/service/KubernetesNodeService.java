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

package com.github.shoothzj.kdash.service;

import com.github.shoothzj.kdash.module.GetNodeResp;
import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.V1Node;
import io.kubernetes.client.openapi.models.V1NodeList;
import io.kubernetes.client.openapi.models.V1NodeStatus;
import io.kubernetes.client.openapi.models.V1NodeSystemInfo;
import io.kubernetes.client.openapi.models.V1ObjectMeta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class KubernetesNodeService {

    private final CoreV1Api k8sClient;

    public KubernetesNodeService(@Autowired ApiClient apiClient) {
        this.k8sClient = new CoreV1Api(apiClient);
    }

    public List<GetNodeResp> getNodes() throws Exception {
        V1NodeList listNode = k8sClient.listNode("true", null,
                null, null, null, null, null,
                null, 30, false);
        List<GetNodeResp> getNodeResps = new ArrayList<>();
        for (V1Node item : listNode.getItems()) {
            V1ObjectMeta metadata = item.getMetadata();
            V1NodeStatus status = item.getStatus();
            GetNodeResp getNodeResp = new GetNodeResp();
            if (metadata != null) {
                getNodeResp.setNodeName(metadata.getName());
                OffsetDateTime timestamp = metadata.getCreationTimestamp();
                assert timestamp != null;
                String date = timestamp.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                getNodeResp.setNodeCreationTimestamp(date);
            }

            if (status != null) {
                V1NodeSystemInfo nodeInfo = status.getNodeInfo();
                assert nodeInfo != null;
                getNodeResp.setNodeKubeletVersion(nodeInfo.getKubeletVersion());
                getNodeResp.setNodeOsImage(nodeInfo.getOsImage());
                getNodeResp.setNodeArchitecture(nodeInfo.getArchitecture());
            }
            getNodeResps.add(getNodeResp);
        }
        return getNodeResps;
    }

}
