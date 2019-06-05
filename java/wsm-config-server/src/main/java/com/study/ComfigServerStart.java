package com.study;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableConfigServer
@EnableDiscoveryClient
public class ComfigServerStart {

    public static void main(String[] args) {
        SpringApplication.run(ComfigServerStart.class, args);
        System.err.println("configServer启动了");
    }
}
