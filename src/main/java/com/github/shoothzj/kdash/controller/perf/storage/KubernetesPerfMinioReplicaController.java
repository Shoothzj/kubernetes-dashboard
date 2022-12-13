package com.github.shoothzj.kdash.controller.perf.storage;

import com.github.shoothzj.kdash.module.perf.storage.CreatePerfMinioReplicaReq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/kubernetes/perf/minio")
public class KubernetesPerfMinioReplicaController {

    @PutMapping("/namespaces/{namespace}/replicas")
    public ResponseEntity<Void> createPerfMinioReplica(@RequestBody CreatePerfMinioReplicaReq req,
                                                       @PathVariable String namespace) {
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
