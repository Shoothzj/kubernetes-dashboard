package com.github.shoothzj.kdash.exception;

import io.kubernetes.client.openapi.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;

import java.util.Map;

@Slf4j
@Service
public class GlobalErrorAttributes extends DefaultErrorAttributes {

    @Override
    public Map<String, Object> getErrorAttributes(ServerRequest request,
                                                  ErrorAttributeOptions options) {
        Map<String, Object> map = super.getErrorAttributes(
                request, options);
        Throwable throwable = getError(request);
        if (throwable instanceof ApiException apiException) {
            map.put("status", HttpStatus.SERVICE_UNAVAILABLE);
            log.error("kubernetes api exception {} {}", apiException.getCode(), apiException.getResponseBody());
        } else {
            log.error("exception is ", throwable);
            map.put("status", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        map.put("message", throwable.getMessage());
        return map;
    }

}
