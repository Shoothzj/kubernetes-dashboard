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

package com.github.shoothzj.kdash.module;

import io.kubernetes.client.openapi.models.V1Container;
import io.kubernetes.client.openapi.models.V1ObjectMeta;
import io.kubernetes.client.openapi.models.V1PodSpec;
import io.kubernetes.client.openapi.models.V1PodTemplateSpec;
import io.kubernetes.client.openapi.models.V1StatefulSetPersistentVolumeClaimRetentionPolicy;
import io.kubernetes.client.openapi.models.V1StatefulSetStatus;
import io.kubernetes.client.openapi.models.V1StatefulSetUpdateStrategy;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Setter
@Getter
public class CreateStatefulSetParam extends BaseParam {

    private String statefulSetName;

    private Map<String, String> labels;

    private int replicas;

    private String image;

    private String imagePullSecret;

    private Map<String, String> env;

    private Map<String, String> annotations;

    private List<ValueFrom> valueFroms;

    private String serviceName;

    private Probe livenessProbe;

    private Probe readinessProbe;

    private NodeSelectorRequirement nodeSelectorRequirement;

    private PodAffinityTerms podAffinityTerms;

    private PodAffinityTerms podAntiAffinityTerms;

    private ResourceRequirements resourceRequirements;

    private List<VolumeClaimTemplates> persistentVolumes;

    private V1StatefulSetStatus v1StatefulSetStatus;

    private V1StatefulSetUpdateStrategy v1StatefulSetUpdateStrategy; // todo

    private V1StatefulSetPersistentVolumeClaimRetentionPolicy policy;

    private String podManagementPolicy;

    private V1PodTemplateSpec v1PodTemplateSpec;

    private V1ObjectMeta specV1ObjectMeta;

    private V1PodSpec v1PodSpec;

    private V1Container specV1Container;

    private String clusterName;


    public CreateStatefulSetParam() {
    }
}
