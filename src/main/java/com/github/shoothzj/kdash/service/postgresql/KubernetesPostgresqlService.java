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

package com.github.shoothzj.kdash.service.postgresql;


import com.github.shoothzj.kdash.module.postgresql.CreatePostgresqlReq;
import com.github.shoothzj.kdash.service.KubernetesServiceService;
import com.github.shoothzj.kdash.service.KubernetesStatefulSetService;
import com.github.shoothzj.kdash.util.PostgresqlUtil;
import io.kubernetes.client.openapi.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KubernetesPostgresqlService {

    private final KubernetesStatefulSetService statefulSetService;

    private final KubernetesServiceService serviceService;

    public KubernetesPostgresqlService(@Autowired KubernetesStatefulSetService statefulSetService,
                                       @Autowired KubernetesServiceService serviceService) {
        this.statefulSetService = statefulSetService;
        this.serviceService = serviceService;
    }

    public void createPostgresql(String namespace, CreatePostgresqlReq req) throws ApiException {
        serviceService.createService(namespace, PostgresqlUtil.service(req));
        statefulSetService.createNamespacedStatefulSet(namespace, PostgresqlUtil.statefulSet(req));
    }

    public void deletePostgresql(String namespace, String statefulSetName) throws ApiException {
        serviceService.deleteService(namespace, statefulSetName);
        statefulSetService.deleteStatefulSet(namespace, statefulSetName);
    }
}
