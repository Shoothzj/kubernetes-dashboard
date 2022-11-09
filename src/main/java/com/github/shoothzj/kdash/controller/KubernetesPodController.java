package com.github.shoothzj.kdash.controller;

import com.github.shoothzj.kdash.service.KubernetesPodService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/kubernetes")
public class KubernetesPodController {

    private final KubernetesPodService kubernetesPodService;

    public KubernetesPodController(@Autowired KubernetesPodService kubernetesPodService) {
        this.kubernetesPodService = kubernetesPodService;
    }
}
