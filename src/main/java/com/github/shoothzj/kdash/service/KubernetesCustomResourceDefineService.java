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

import com.github.shoothzj.kdash.module.GetCustomResourceDefinitionResp;
import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.apis.ApiextensionsV1Api;
import io.kubernetes.client.openapi.models.V1CustomResourceDefinition;
import io.kubernetes.client.openapi.models.V1CustomResourceDefinitionList;
import io.kubernetes.client.openapi.models.V1ObjectMeta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KubernetesCustomResourceDefineService {

    private final ApiextensionsV1Api apiextensionsV1Api;

    public KubernetesCustomResourceDefineService(@Autowired ApiClient apiClient) {
        this.apiextensionsV1Api = new ApiextensionsV1Api(apiClient);
    }

    public void createCustomResourceDefinition() throws ApiException {
        V1CustomResourceDefinition customResourceDefinition = new V1CustomResourceDefinition();
        apiextensionsV1Api.createCustomResourceDefinition(customResourceDefinition,
                "true", null, null, null);
    }

    public List<GetCustomResourceDefinitionResp> getCustomResourceDefinitionList() throws ApiException {
        V1CustomResourceDefinitionList definitionList = apiextensionsV1Api.listCustomResourceDefinition("true",
                null, null, null, null, null, null,
                null, 30, false);
        List<V1CustomResourceDefinition> definitionListItems = definitionList.getItems();
        return definitionListItems.stream().map(this::convert).toList();
    }

    private GetCustomResourceDefinitionResp convert(V1CustomResourceDefinition v1CustomResourceDefinition) {
        GetCustomResourceDefinitionResp resp = new GetCustomResourceDefinitionResp();
        V1ObjectMeta metadata = v1CustomResourceDefinition.getMetadata();
        if (metadata != null) {
            resp.setName(metadata.getName());
        }
        return resp;
    }

}
