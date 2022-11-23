/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.github.shoothzj.kdash.module;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.util.Map;

@Getter
@Setter
public class CreateChaosMeshCrdObjectReq {

    @NotEmpty
    private String name;

    private String namespaces = "default";

    private String targetNamespace = "default";

    @NotEmpty
    private String mode;

    @NotEmpty
    private String value;

    @NotEmpty
    private String duration;

    @NotEmpty
    private Map<String, String> labelSelectors;

    public CreateChaosMeshCrdObjectReq() {
    }

}
