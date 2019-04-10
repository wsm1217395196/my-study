package com.study;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class ComfigServerStart {

    public static void main(String[] args) {
        SpringApplication.run(ComfigServerStart.class, args);
        System.err.println("configServer启动了");
    }
}
