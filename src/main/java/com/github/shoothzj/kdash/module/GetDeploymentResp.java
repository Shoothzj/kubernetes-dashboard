package com.github.shoothzj.kdash.module;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class GetDeploymentResp {

    private String namespace;

    private String deployName;

    private int replicas;

    private int availableReplicas;

    private String creationTimestamp;

    private List<ContainerInfo> containerInfoList;

    public GetDeploymentResp() {
    }
}
