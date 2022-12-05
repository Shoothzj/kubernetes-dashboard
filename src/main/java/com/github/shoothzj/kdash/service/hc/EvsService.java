package com.github.shoothzj.kdash.service.hc;

import com.github.shoothzj.kdash.config.CloudConfig;
import com.github.shoothzj.kdash.config.HuaweiCloudConfig;
import com.huaweicloud.sdk.core.auth.BasicCredentials;
import com.huaweicloud.sdk.evs.v2.EvsAsyncClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EvsService {

    private final EvsAsyncClient evsClient;

    public EvsService(@Autowired CloudConfig cloudConfig, @Autowired HuaweiCloudConfig config) {
        evsClient = EvsAsyncClient.newBuilder()
                .withEndpoint(config.swrEndpoint)
                .withCredential(new BasicCredentials()
                        .withAk(cloudConfig.accessKey)
                        .withSk(cloudConfig.secretKey)
                        .withProjectId(config.projectId)
                )
                .build();
    }
}
