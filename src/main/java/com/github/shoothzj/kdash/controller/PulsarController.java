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

import com.github.shoothzj.kdash.module.CreateDeploymentReq;
import com.github.shoothzj.kdash.service.KubernetesDeployService;
import com.github.shoothzj.kdash.util.KubernetesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/pulsar")
public class PulsarController {

    private final KubernetesDeployService kubernetesDeployService;

    public PulsarController(@Autowired KubernetesDeployService kubernetesDeployService) {
        this.kubernetesDeployService = kubernetesDeployService;
    }

    @PostMapping("/namespace/{namespace}/consumer/create")
    public ResponseEntity<Void> createConsumerModel(@RequestParam int podNumber,
                                                    @RequestBody CreateDeploymentReq req) throws Exception {
        for (int i = 0; i < podNumber; i++) {
            req.setDeploymentName(req.getDeploymentName() + "-" + i);
            kubernetesDeployService.createNamespacedDeploy(req);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/namespace/{namespace}/consumer/deploy-name/{deployName}")
    public ResponseEntity<Void> deleteConsumerModel(@PathVariable String deployName,
                                                    @RequestParam int podNumber,
                                                    @PathVariable String namespace) throws Exception {
        for (int i = 0; i < podNumber; i++) {
           kubernetesDeployService.deleteDeploy(namespace, deployName + "-" + i);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/namespace/{namespace}/producer/create")
    public ResponseEntity<Void> createProducerModel(@RequestParam int podNumber,
                                                    @RequestBody CreateDeploymentReq req) throws Exception {
        req.setReplicas(req.getReplicas() == 0 ? 1 : req.getReplicas());
        req.setResourceRequirements(req.getResourceRequirements() != null
        ? req.getResourceRequirements() : KubernetesUtil.defaultResourceRequirements());
        Map<String, String> env = req.getEnv();
        String subscriptionName = env.getOrDefault("PULSAR_SUBSCRIPTION_NAME", req.getDeploymentName());
        for (int i = 0; i < podNumber; i++) {
            String deploymentName = req.getDeploymentName() + "-" + i;
            req.setDeploymentName(deploymentName);
            env.put("PULSAR_SUBSCRIPTION_NAME", subscriptionName + i);
            req.setEnv(env);
            kubernetesDeployService.createNamespacedDeploy(req);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/namespace/{namespace}/producer/deploy-name/{deployName}")
    public ResponseEntity<Void> deleteProducerModel(@PathVariable String deployName,
                                                    @RequestParam int podNumber,
                                                    @PathVariable String namespace) throws Exception {
        for (int i = 0; i < podNumber; i++) {
            kubernetesDeployService.deleteDeploy(namespace, deployName + "-" + i);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
