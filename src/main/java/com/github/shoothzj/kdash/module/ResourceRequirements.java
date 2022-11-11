package com.github.shoothzj.kdash.module;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Setter
@Getter
public class ResourceRequirements {

    private Map<String, String> limits;

    private Map<String, String> requests;

    public ResourceRequirements() {
    }
}
