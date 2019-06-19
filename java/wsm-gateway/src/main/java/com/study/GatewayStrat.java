package com.study;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class GatewayStrat {

    public static void main(String[] args) {
        SpringApplication.run(GatewayStrat.class, args);
        System.err.println("网关wsm-gateway启动了！！");
    }
}
