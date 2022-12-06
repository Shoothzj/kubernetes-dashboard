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

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Setter
@Getter
public class CreateStatefulSetReq {

    private String namespace;

    private String statefulSetName;

    private int replicas;

    private String image;

    private String imagePullSecret;

    private Map<String, String> env;

    private Map<String, String> annotations;

    private Probe livenessProbe;

    private Probe readinessProbe;

    private NodeSelectorRequirement nodeSelectorRequirement;

    private PodAffinityTerms podAffinityTerms;

    private PodAffinityTerms podAntiAffinityTerms;

    private ResourceRequirements resourceRequirements;

    private List<VolumeClaimTemplates> persistentVolumes;

    public CreateStatefulSetReq() {
    }
}