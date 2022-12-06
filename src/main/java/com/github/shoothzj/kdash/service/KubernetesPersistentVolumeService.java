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

import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.V1PersistentVolume;
import org.springframework.beans.factory.annotation.Autowired;

public class KubernetesPersistentVolumeService {

    private final CoreV1Api coreV1Api;

    public KubernetesPersistentVolumeService(@Autowired ApiClient apiClient) {
        this.coreV1Api = new CoreV1Api(apiClient);
    }

    public void createPersistentVolume(V1PersistentVolume v1PersistentVolume) throws ApiException {
        coreV1Api.createPersistentVolume(v1PersistentVolume, "true", null, null, null);
    }

    public void deletePersistentVolume(String name) throws ApiException {
        coreV1Api.deletePersistentVolume(name, "true", null, null, null, null, null);
    }
}
