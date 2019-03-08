package com.study;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.openfeign.EnableFeignClients;


@MapperScan("com.study.mapper")
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
//@EnableHystrix
@EnableHystrixDashboard
//@EnableCircuitBreaker
public class UserStart {

    public static void main(String[] args) {
        SpringApplication.run(UserStart.class, args);
        System.err.println("user启动了！！！");
    }

}

