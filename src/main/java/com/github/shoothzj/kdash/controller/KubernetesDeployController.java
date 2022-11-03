package com.github.shoothzj.kdash.controller;

import com.github.shoothzj.kdash.module.CreateDeploymentReq;
import com.github.shoothzj.kdash.service.KubernetesDeployService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/kubernetes")
@Slf4j
public class KubernetesDeployController {

    public KubernetesDeployService deployService;

    public KubernetesDeployController(@Autowired KubernetesDeployService deployService) {
        this.deployService = deployService;
    }

    @PostMapping("/deployments")
    public ResponseEntity<Void> createDeployment(@RequestBody CreateDeploymentReq req) throws Exception {
        deployService.createNamespacedDeployment(req);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
