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

import com.github.shoothzj.kdash.module.GetNodeResp;
import com.github.shoothzj.kdash.service.KubernetesService;
import com.github.shoothzj.kdash.module.CreateDeploymentReq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/kubernetes/api")
@Slf4j
public class KubernetesController {

    public KubernetesService kubernetesService;

    public KubernetesController(@Autowired KubernetesService kubernetesService) {
        this.kubernetesService = kubernetesService;
    }

    @GetMapping("/nodes")
    public ResponseEntity<List<GetNodeResp>> getNodes() throws Exception {
        return new ResponseEntity<>(kubernetesService.getNodes(), HttpStatus.OK);
    }

    @PostMapping("/deployments")
    public ResponseEntity<Void> createDeployment(@RequestBody CreateDeploymentReq req) throws Exception {
        kubernetesService.createNamespacedDeployment(req);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
