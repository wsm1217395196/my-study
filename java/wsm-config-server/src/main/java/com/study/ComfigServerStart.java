package com.study;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableConfigServer
@EnableEurekaClient
@SpringBootApplication
public class ComfigServerStart {

    public static void main(String[] args) {
        SpringApplication.run(ComfigServerStart.class, args);
        System.err.println("configServer启动了");
    }
}
