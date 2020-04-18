package com.study.controller;

import com.study.result.ResultView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * rabbitmq生产者
 */
@Api(tags = "rabbitmq生产者")
@RestController
@RequestMapping("/rabbitProducer")
public class RabbitProducerController {

    @Autowired
    private AmqpTemplate amqpTemplate;

    @ApiOperation(value = "根据服务名刷新权限")
    @GetMapping("/loadResourceDefine")
    public ResultView loadResourceDefine(@ApiParam("服务名,多个用逗号隔开") @RequestParam String[] serverNames) {
        for (String serverName : serverNames) {
            amqpTemplate.convertAndSend(serverName, "", serverName + "刷新权限");
        }
        return ResultView.success();
    }
}
