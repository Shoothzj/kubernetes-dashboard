/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.github.shoothzj.kdash.module.chaosmesh;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigInteger;
import java.util.List;

@Setter
@Getter
@ToString
public class NetworkChaos extends BaseChaos<NetworkChaos.NetworkChaosAction> {

    public enum NetworkChaosAction {
        delay,
        loss,
        duplicate,
        corrupt,
        partition,
        bandwidth
    }

    public enum NetworkChaosDirection {
        to,
        from,
        both
    }

    @Setter
    @Getter
    public static class Delay {

        private String latency;

        private String correlation;

        private String jitter;

        private Reorder reorder;

        public Delay() {
        }

        @Setter
        @Getter
        public static class Reorder {

            private String reorder;

            private String correlation;

            private Integer gap;

        }
    }

    @Setter
    @Getter
    public static class Loss {

        private String loss;

        private String correlation;

        public Loss() {
        }
    }

    @Setter
    @Getter
    public static class Corrupt {

        private String corrupt;

        private String correlation;

        public Corrupt() {
        }
    }

    @Setter
    @Getter
    public static class Duplicate {

        private String duplicate;

        private String correlation;

        public Duplicate() {
        }
    }

    @Setter
    @Getter
    public static class Bandwidth{

        private String rate;

        private Long limit;

        private Long buffer;

        private BigInteger peakrate;

        private Long minburst;

        public Bandwidth() {
        }
    }

    public NetworkChaos() {
        super(ChaosKind.NetworkChaos);
    }

    private NetworkChaosDirection direction;

    private List<String> externalTargets;

    private Delay delay;

    private Loss loss;

    private Corrupt corrupt;

    private Duplicate duplicate;

    // for partition action
    private ChaosSpec<NetworkChaos.NetworkChaosAction> target;

    private Bandwidth bindwidth;

    private String device;

}
