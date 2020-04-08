package com.study;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@MapperScan("com.study.mapper")
@EnableZuulProxy
@SpringBootApplication
@EnableDiscoveryClient
public class ZullStrat {

    public static void main(String[] args) {
        SpringApplication.run(ZullStrat.class, args);
        System.err.println("网关wsm-zull启动了！！");
    }

}
