package com.study.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class RabbitConfig {

    @Value("${my-config.loadResourceDefineQueue}")
    private String loadResourceDefineQueue;

    @Value("${spring.application.name}")
    private String serviceName;

    /**
     * 配置订阅队列
     *
     * @return
     */
    @Bean
    public Queue queue() {
        return new Queue(loadResourceDefineQueue);
    }

    /**
     * 声明一个Fanout类型的交换机
     *
     * @return
     */
    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(serviceName);
    }

    /**
     * 队列绑定交换机
     *
     * @return
     */
    @Bean
    public Binding binding() {
        return BindingBuilder.bind(queue()).to(fanoutExchange());
    }
}
