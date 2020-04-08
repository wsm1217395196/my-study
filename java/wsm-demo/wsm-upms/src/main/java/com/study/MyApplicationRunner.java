package com.study;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 在SpringBoot中，有两种接口方式实现启动执行，
 * 分别是ApplicationRunner和CommandLineRunner，除了可接受参数不同，其他的大同小异。
 * 我们也可以设置Order来设定执行的顺序，
 * 分别是注解@Order(value = 1）和实现接口implements Ordered的方式
 */
@Component
@Order(value = 1)
public class MyApplicationRunner implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {

    }
}
