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

package com.github.shoothzj.kdash.controller.postgresql;

import com.github.shoothzj.kdash.module.postgresql.CreatePostgresqlReq;
import com.github.shoothzj.kdash.service.postgresql.KubernetesPostgresqlService;
import io.kubernetes.client.openapi.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/kubernetes/postgresql")
public class KubernetesPostgresqlController {

    private final KubernetesPostgresqlService postgresqlService;

    public KubernetesPostgresqlController(@Autowired KubernetesPostgresqlService postgresqlService) {
        this.postgresqlService = postgresqlService;
    }

    @PutMapping("/namespace/{namespace}/stateful-sets")
    public ResponseEntity<Void> createPostgresql(@PathVariable String namespace,
                                                 @RequestBody CreatePostgresqlReq req) throws ApiException {
        postgresqlService.createPostgresql(namespace, req);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @DeleteMapping("/namespace/{namespace}/stateful-sets/{statefulSetName}")
    public ResponseEntity<Void> deletePostgresql(@PathVariable String namespace,
                                                 @PathVariable String statefulSetName) throws ApiException {
        postgresqlService.deletePostgresql(namespace, statefulSetName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
