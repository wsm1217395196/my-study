package com.study;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;


@SpringBootApplication
@EnableEurekaServer
public class EurekaServerStart {

    public static void main(String[] args) {
        SpringApplication.run(EurekaServerStart.class, args);
        System.out.println("EurekaServer启动了！！！");
    }

}
