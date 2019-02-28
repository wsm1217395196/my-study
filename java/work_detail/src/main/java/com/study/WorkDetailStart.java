package com.study;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@MapperScan("com.study.mapper")
@SpringBootApplication
@EnableDiscoveryClient
public class WorkDetailStart {

    public static void main(String[] args) {
        SpringApplication.run(WorkDetailStart.class, args);
        System.err.println("workDetail启动了！！！");
    }

}

