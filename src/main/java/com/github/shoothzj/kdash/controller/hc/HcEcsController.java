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

package com.github.shoothzj.kdash.controller.hc;

import com.github.shoothzj.kdash.service.hc.HcEcsService;
import com.huaweicloud.sdk.ecs.v2.model.ListServersDetailsRequest;
import com.huaweicloud.sdk.ecs.v2.model.ListServersDetailsResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Slf4j
@RestController
@RequestMapping("/api/hc/ecs")
public class HcEcsController {

    private final HcEcsService ecsService;

    public HcEcsController(@Autowired HcEcsService ecsService) {
        this.ecsService = ecsService;
    }

    @PostMapping("servers/changeos/{imageId}")
    public ResponseEntity<Void> changeos(@PathVariable String imageId, @RequestBody List<String> serverIds)
            throws ExecutionException, InterruptedException {
        ecsService.changeos(serverIds, imageId).get();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("servers/detail")
    public ResponseEntity<ListServersDetailsResponse> getServers() throws ExecutionException, InterruptedException {
        return new ResponseEntity<>(ecsService.getEcsServerList(new ListServersDetailsRequest()).get(), HttpStatus.OK);
    }

}
