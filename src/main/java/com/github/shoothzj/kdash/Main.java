package com.github.shoothzj.kdash;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.FileSystemResource;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import java.io.File;

@SpringBootApplication
public class Main {

    private final String staticPath;

    {
        String aux = System.getenv("STATIC_PATH");
        if (aux.endsWith(File.separator)) {
            staticPath = aux;
        } else {
            staticPath = aux + File.separator;
        }
    }

    @Bean
    RouterFunction<ServerResponse> homePageRouter() {
        return RouterFunctions.route(RequestPredicates.GET("/"),
                request -> ServerResponse.ok().bodyValue(new FileSystemResource(staticPath + "index.html")));
    }

    @Bean
    RouterFunction<ServerResponse> staticResourceRouter() {
        return RouterFunctions.resources("/**", new FileSystemResource(staticPath));
    }

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

}
