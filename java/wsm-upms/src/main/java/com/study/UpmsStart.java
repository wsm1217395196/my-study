package com.study;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@MapperScan("com.study.mapper")
@SpringBootApplication
@EnableDiscoveryClient
public class UpmsStart {

    public static void main(String[] args) {
        SpringApplication.run(UpmsStart.class, args);
        System.err.println("upms启动了！！！");
    }

}
