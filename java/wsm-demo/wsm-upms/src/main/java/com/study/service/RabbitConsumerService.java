package com.study.service;

import com.study.config.MySecurityMetadataSource;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * rabbitmq消费者
 */
@Component
public class RabbitConsumerService {

    @Autowired
    private MySecurityMetadataSource mySecurityMetadataSource;

    /**
     * 刷新权限
     *
     * @param msg
     */
    @RabbitListener(queues = "wsm-upms-queue1")
    public void loadResourceDefine(String msg) {
        mySecurityMetadataSource.loadResourceDefine();
    }
}
