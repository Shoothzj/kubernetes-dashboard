package com.github.shoothzj.kdash.controller;

import com.github.shoothzj.kdash.service.KubernetesReplicaControllerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/kubernetes")
public class KubernetesReplicaControllerController {

    private final KubernetesReplicaControllerService kubernetesReplicaControllerService;

    public KubernetesReplicaControllerController(
            @Autowired KubernetesReplicaControllerService kubernetesReplicaControllerService) {
        this.kubernetesReplicaControllerService = kubernetesReplicaControllerService;
    }

}
