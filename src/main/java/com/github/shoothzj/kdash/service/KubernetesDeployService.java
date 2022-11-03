/**
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

import com.github.shoothzj.kdash.module.CreateDeploymentReq;
import com.github.shoothzj.kdash.util.KubernetesUtil;
import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.apis.AppsV1Api;
import io.kubernetes.client.openapi.models.V1Deployment;
import io.kubernetes.client.openapi.models.V1DeploymentSpec;
import io.kubernetes.client.openapi.models.V1ObjectMeta;
import io.kubernetes.client.openapi.models.V1PodSpec;
import io.kubernetes.client.openapi.models.V1PodTemplateSpec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KubernetesDeployService {

    private final AppsV1Api appsV1Api;

    public KubernetesDeployService(@Autowired ApiClient apiClient) {
        this.appsV1Api = new AppsV1Api(apiClient);
    }

    public void createNamespacedDeployment(CreateDeploymentReq req) throws Exception {
        // deploy
        V1Deployment deployment = new V1Deployment();
        deployment.setApiVersion("apps/v1");
        deployment.setKind("Deployment");

        {
            // metadata
            V1ObjectMeta metadata = new V1ObjectMeta();
            metadata.setName(req.getDeploymentName());
            metadata.setNamespace(req.getNamespace());
            metadata.setLabels(KubernetesUtil.label(req.getDeploymentName()));
            deployment.setMetadata(metadata);
        }

        {
            // spec
            V1DeploymentSpec deploySpec = new V1DeploymentSpec();
            // spec replicas
            deploySpec.setReplicas(req.getReplicas());
            // spec selector
            deploySpec.setSelector(KubernetesUtil.labelSelector(req.getDeploymentName()));
            // spec template
            V1PodTemplateSpec templateSpec = new V1PodTemplateSpec();
            {
                // template metadata
                V1ObjectMeta templateMetadata = new V1ObjectMeta();
                templateMetadata.setLabels(KubernetesUtil.label(req.getDeploymentName()));
                templateSpec.setMetadata(templateMetadata);
            }
            // spec template spec
            V1PodSpec v1PodSpec = new V1PodSpec();
            // spec template spec containers
            v1PodSpec.setContainers(KubernetesUtil.singleContainerList(req.getImage(), req.getEnv()));
            templateSpec.setSpec(v1PodSpec);
            deploySpec.setTemplate(templateSpec);
            deployment.setSpec(deploySpec);
        }

        appsV1Api.createNamespacedDeployment(req.getNamespace(), deployment,
                "true", null, null, null);
    }

}
