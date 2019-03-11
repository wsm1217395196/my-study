package com.study;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;


@SpringBootApplication
@EnableEurekaServer
public class DiscoveryStart {

    public static void main(String[] args) {
        SpringApplication.run(DiscoveryStart.class, args);
        System.err.println("EurekaServer启动了！！！");
    }

}
