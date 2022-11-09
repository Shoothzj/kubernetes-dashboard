package com.github.shoothzj.kdash.module.chaosmesh.networkchaos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Delay {

    private String latency;

    private String correlation;

    private String jitter;

    private Reorder reorder;

    public Delay() {
    }

}
