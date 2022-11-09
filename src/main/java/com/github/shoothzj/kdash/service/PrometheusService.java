package com.github.shoothzj.kdash.service;

import com.github.shoothzj.kdash.config.PrometheusConfig;
import io.github.protocol.prom.PromApiClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PrometheusService {

    private final PromApiClient promApiClient;

    public PrometheusService(@Autowired PrometheusConfig config) {
        this.promApiClient = PromApiClient.builder().host(config.host).port(config.port).build();
    }
}
