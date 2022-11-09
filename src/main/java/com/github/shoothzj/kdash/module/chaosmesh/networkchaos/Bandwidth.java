package com.github.shoothzj.kdash.module.chaosmesh.networkchaos;

import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;

@Setter
@Getter
public class Bandwidth{

    private String rate;

    private Long limit;

    private Long buffer;

    private BigInteger peakrate;

    private Long minburst;

    public Bandwidth() {
    }
}
