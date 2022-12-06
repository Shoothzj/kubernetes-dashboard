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

import com.github.shoothzj.kdash.module.ContainerInfo;
import com.github.shoothzj.kdash.module.CreateDeploymentReq;
import com.github.shoothzj.kdash.module.GetDeploymentResp;
import com.github.shoothzj.kdash.module.ScaleReq;
import com.github.shoothzj.kdash.util.KubernetesUtil;
import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.apis.AppsV1Api;
import io.kubernetes.client.openapi.models.V1Container;
import io.kubernetes.client.openapi.models.V1Deployment;
import io.kubernetes.client.openapi.models.V1DeploymentList;
import io.kubernetes.client.openapi.models.V1DeploymentSpec;
import io.kubernetes.client.openapi.models.V1DeploymentStatus;
import io.kubernetes.client.openapi.models.V1ObjectMeta;
import io.kubernetes.client.openapi.models.V1PodSpec;
import io.kubernetes.client.openapi.models.V1PodTemplateSpec;
import io.kubernetes.client.openapi.models.V1Scale;
import io.kubernetes.client.openapi.models.V1ScaleSpec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class KubernetesDeployService {

    private final AppsV1Api appsV1Api;

    public KubernetesDeployService(@Autowired ApiClient apiClient) {
        this.appsV1Api = new AppsV1Api(apiClient);
    }

    public void createNamespacedDeploy(CreateDeploymentReq req) throws Exception {
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
                // object metadata
                V1ObjectMeta objectMeta = new V1ObjectMeta();
                objectMeta.setLabels(KubernetesUtil.label(req.getDeploymentName()));
                templateSpec.setMetadata(objectMeta);
            }
            // spec template spec
            V1PodSpec v1PodSpec = new V1PodSpec();
            // spec template spec containers
            v1PodSpec.setContainers(KubernetesUtil.singleContainerList(req.getImage(), req.getEnv(),
                    req.getDeploymentName(), req.getResourceRequirements()));
            v1PodSpec.setImagePullSecrets(KubernetesUtil.imagePullSecrets(req.getImagePullSecret()));
            templateSpec.setSpec(v1PodSpec);
            deploySpec.setTemplate(templateSpec);
            deployment.setSpec(deploySpec);
        }

        appsV1Api.createNamespacedDeployment(req.getNamespace(), deployment,
                "true", null, null, null);
    }

    public void deleteDeploy(String namespace, String deployName) throws ApiException {
        appsV1Api.deleteNamespacedDeployment(deployName, namespace, "true",
                null, 30, false, null, null);
    }

    public List<GetDeploymentResp> getNamespaceDeployments(String namespace) throws ApiException {
        V1DeploymentList v1DeploymentList = appsV1Api.listNamespacedDeployment(namespace, "true",
                null, null, null, null,
                null, null, null, null, null);
        List<GetDeploymentResp> result = new ArrayList<>();
        for (V1Deployment deploy : v1DeploymentList.getItems()) {
            result.add(convert(deploy));
        }
        return result;
    }

    private GetDeploymentResp convert(V1Deployment deploy) {
        GetDeploymentResp getDeploymentResp = new GetDeploymentResp();
        V1ObjectMeta metadata = deploy.getMetadata();
        if (metadata != null) {
            getDeploymentResp.setDeployName(metadata.getName());
            OffsetDateTime timestamp = metadata.getCreationTimestamp();
            if (timestamp != null) {
                getDeploymentResp.setCreationTimestamp(timestamp.format(DateTimeFormatter.ISO_DATE_TIME));
            }
        }
        V1DeploymentStatus status = deploy.getStatus();
        if (status != null) {
            getDeploymentResp.setReplicas(status.getReplicas() == null ? 0 : status.getReplicas());
            getDeploymentResp.setAvailableReplicas(status.getAvailableReplicas() == null
                    ? 0 : status.getAvailableReplicas());
        }
        V1DeploymentSpec spec = deploy.getSpec();
        if (spec != null) {
            V1PodSpec v1PodSpec = spec.getTemplate().getSpec();
            if (v1PodSpec == null) {
                return getDeploymentResp;
            }
            List<V1Container> containers = v1PodSpec.getContainers();
            List<ContainerInfo> containerInfoList = KubernetesUtil.containerInfoList(containers);
            getDeploymentResp.setContainerInfoList(containerInfoList);
        }
        return getDeploymentResp;
    }

    public void scaleDeploy(String namespace, ScaleReq req) throws ApiException {
        V1Scale v1Scale = new V1Scale();
        V1ScaleSpec v1ScaleSpec = new V1ScaleSpec();
        v1ScaleSpec.setReplicas(req.getReplicas());
        v1Scale.setSpec(v1ScaleSpec);
        V1ObjectMeta objectMeta = new V1ObjectMeta();
        objectMeta.setName(req.getAppName());
        objectMeta.setNamespace(namespace);
        objectMeta.setLabels(KubernetesUtil.label(req.getAppName()));
        v1Scale.setMetadata(objectMeta);
        appsV1Api.replaceNamespacedDeploymentScale(req.getAppName(), namespace, v1Scale, "true",
                null, null, null);
    }
}
