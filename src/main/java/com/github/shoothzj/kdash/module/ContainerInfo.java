package com.github.shoothzj.kdash.module;

import io.kubernetes.client.openapi.models.V1ContainerPort;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Setter
@Getter
public class ContainerInfo {

    private String containerName;

    private String image;

    private Map<String, String> env;

    private List<V1ContainerPort> ports;

    public ContainerInfo() {
    }
}
