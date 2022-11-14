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

package com.github.shoothzj.kdash.util;

import com.github.shoothzj.kdash.module.ContainerInfo;
import com.github.shoothzj.kdash.module.ResourceRequirements;
import io.kubernetes.client.custom.Quantity;
import io.kubernetes.client.openapi.models.V1Container;
import io.kubernetes.client.openapi.models.V1EnvVar;
import io.kubernetes.client.openapi.models.V1LabelSelector;
import io.kubernetes.client.openapi.models.V1LocalObjectReference;
import io.kubernetes.client.openapi.models.V1ResourceRequirements;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class KubernetesUtil {

    public static Map<String, String> label(String deployName) {
        Map<String, String> map = new HashMap<>();
        map.put("app", deployName);
        return map;
    }

    public static V1LabelSelector labelSelector(String deployName) {
        V1LabelSelector labelSelector = new V1LabelSelector();
        Map<String, String> matchLabels = new HashMap<>();
        matchLabels.put("app", deployName);
        labelSelector.setMatchLabels(matchLabels);
        return labelSelector;
    }

    public static List<V1Container> singleContainerList(String image,
                                                        Map<String, String> envMap,
                                                        String name,
                                                        ResourceRequirements resourceRequirements) {
        List<V1Container> containers = new ArrayList<>();
        V1Container container = new V1Container();
        container.setName(name);
        container.setImage(image);
        container.setEnv(envMap.entrySet().stream().map(entry -> {
            V1EnvVar envVar = new V1EnvVar();
            envVar.setName(entry.getKey());
            envVar.setValue(entry.getValue());
            return envVar;
        }).collect(Collectors.toList()));
        V1ResourceRequirements v1ResourceRequirements = new V1ResourceRequirements();
        Map<String, Quantity> limitMap = new HashMap<>();
        for (Map.Entry<String, String> entry : resourceRequirements.getLimits().entrySet()) {
            limitMap.put(entry.getKey(), new Quantity(entry.getValue()));
        }
        v1ResourceRequirements.setLimits(limitMap);
        Map<String, Quantity> requestMap = new HashMap<>();
        for (Map.Entry<String, String> entry : resourceRequirements.getRequests().entrySet()) {
            requestMap.put(entry.getKey(), new Quantity(entry.getValue()));
        }
        v1ResourceRequirements.setRequests(requestMap);
        container.setResources(v1ResourceRequirements);
        containers.add(container);
        return containers;
    }

    public static Map<String, String> envToMap(@Nullable List<V1EnvVar> env) {
        if (env == null) {
            return new HashMap<>();
        }
        Map<String, String> map = new HashMap<>();
        for (V1EnvVar v1EnvVar : env) {
            map.put(v1EnvVar.getName(), v1EnvVar.getValue());
        }
        return map;
    }

    public static List<V1LocalObjectReference> imagePullSecrets(String imagePullSecret) {
        List<V1LocalObjectReference> result = new ArrayList<>();
        V1LocalObjectReference v1LocalObjectReference = new V1LocalObjectReference();
        v1LocalObjectReference.setName(imagePullSecret);
        result.add(v1LocalObjectReference);
        return result;
    }

    public static List<ContainerInfo> containerInfoList(List<V1Container> containers) {
        List<ContainerInfo> containerInfoList = new ArrayList<>(containers.size());
        for (V1Container container : containers) {
            ContainerInfo containerInfo = new ContainerInfo();
            containerInfo.setImage(container.getImage());
            containerInfo.setEnv(KubernetesUtil.envToMap(container.getEnv()));
            containerInfo.setPorts(container.getPorts());
            containerInfoList.add(containerInfo);
        }
        return containerInfoList;
    }

    public static ResourceRequirements defaultResourceRequirements() {
        ResourceRequirements resourceRequirements = new ResourceRequirements();
        Map<String, String> limits = new HashMap<>();
        limits.put("cpu", "0.2");
        limits.put("memory", "200M");
        resourceRequirements.setLimits(limits);
        Map<String, String> requests = new HashMap<>();
        limits.put("cpu", "0.2");
        limits.put("memory", "200M");
        resourceRequirements.setRequests(requests);
        return resourceRequirements;
    }

}
