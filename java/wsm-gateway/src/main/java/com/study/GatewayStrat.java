package com.study;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
//@EnableOAuth2Sso
public class GatewayStrat {

    public static void main(String[] args) {
        SpringApplication.run(GatewayStrat.class, args);
        System.err.println("网关wsm-gateway启动了！！");
    }

    /**
     * 网关路由有两种方式：1、bean方式，2、yml配置文件方式
     * filters设置了熔断器
     *
     * @param builder
     * @return
     */
//    @Bean
//    public RouteLocator myRouteLocator(RouteLocatorBuilder builder) {
//        return builder.routes().
//                route(r -> r.path("/wsm-public/**").
//                        filters(f -> f.hystrix(h -> h.setName("public-hystrix").setFallbackUri("forward:/common/fallback")).
//                                stripPrefix(1)).uri("lb:/wsm-public")).
//                route(r -> r.path("/wsm-upms/**").
//                        filters(f -> f.hystrix(h -> h.setName("user-hystrix").setFallbackUri("forward:/common/fallback")).
//                                stripPrefix(1)).uri("lb://wsm-upms")).
//                route(r -> r.path("/wsm-work/**").
//                        filters(f -> f.hystrix(h -> h.setName("work-hystrix").setFallbackUri("forward:/common/fallback")).
//                                stripPrefix(1)).uri("lb://wsm-work")).
//                route(r -> r.path("/wsm-demo/**").
//                        filters(f -> f.hystrix(h -> h.setName("demo-hystrix").setFallbackUri("forward:/common/fallback")).
//                                stripPrefix(1)).uri("lb://wsm-demo"))
//                .build();
//    }

}
