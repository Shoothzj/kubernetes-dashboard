package com.github.shoothzj.kdash.util;

import io.kubernetes.client.openapi.models.V1Container;
import io.kubernetes.client.openapi.models.V1EnvVar;
import io.kubernetes.client.openapi.models.V1LabelSelector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class KubernetesUtil {

    public static Map<String, String> label(String deployName) {
        Map<String, String> map = new HashMap<>();
        map.put("app", deployName);
        return map;
    }

    public static V1LabelSelector labelSelector(String deployName) {
        V1LabelSelector labelSelector = new V1LabelSelector();
        Map<String, String> matchLabels = new HashMap<>();
        matchLabels.put("app", deployName);
        labelSelector.setMatchLabels(matchLabels);
        return labelSelector;
    }

    public static List<V1Container> singleContainerList(String image, Map<String, String> envMap) {
        List<V1Container> containers = new ArrayList<>();
        V1Container container = new V1Container();
        container.setImage(image);
        container.setEnv(envMap.entrySet().stream().map(entry -> {
            V1EnvVar envVar = new V1EnvVar();
            envVar.setName(entry.getKey());
            envVar.setValue(entry.getValue());
            return envVar;
        }).collect(Collectors.toList()));
        containers.add(container);
        return containers;
    }

}
