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

package com.github.shoothzj.kdash.controller;

import com.github.shoothzj.kdash.module.CreateServiceReq;
import com.github.shoothzj.kdash.module.DeleteServiceReq;
import com.github.shoothzj.kdash.module.GetServiceResp;
import com.github.shoothzj.kdash.service.KubernetesServiceService;
import io.kubernetes.client.openapi.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/kubernetes")
public class KubernetesServiceController {

    private KubernetesServiceService kubernetesServiceService;

    public KubernetesServiceController(@Autowired KubernetesServiceService kubernetesServiceService) {
        this.kubernetesServiceService = kubernetesServiceService;
    }

    @GetMapping("/namespace/{namespace}/services")
    public ResponseEntity<List<GetServiceResp>> getService(@PathVariable String namespace) throws ApiException {
        return new ResponseEntity<>(kubernetesServiceService.getService(namespace), HttpStatus.OK);
    }

    @DeleteMapping("/namespace/{namespace}/services")
    public  ResponseEntity<Void> deleteService(@PathVariable String namespace,
                                               @RequestBody DeleteServiceReq req) throws ApiException {
        kubernetesServiceService.deleteService(namespace, req);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/namespace/{namespace}/services")
    public ResponseEntity<Void> createService(@PathVariable String namespace,
                                              @RequestBody CreateServiceReq req) throws ApiException{
        kubernetesServiceService.createService(namespace, req);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
