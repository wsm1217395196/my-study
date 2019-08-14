package com.study.controller;

import com.alibaba.fastjson.JSONObject;
import com.study.config.MyConfig;
import com.study.feign.WorkFeign;
import com.study.result.ResultView;
import com.study.service.TransactionProducer;
import com.study.utils.CreateUtil;
import io.swagger.annotations.ApiOperation;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试控制器
 */
@RestController
@RequestMapping("/test")
public class TestCotroller {

    @Autowired
    private MyConfig myConfig;
    @Autowired
    private TransactionProducer transactionProducer;
    @Autowired
    private WorkFeign workFeign;

    /**
     * 测试rocketmq分布式事务
     * 利用消息队列的特性：生产的消息一定会被消费，从而达到最终一致性
     *
     * @return
     */
    @ApiOperation(value = "测试rocketmq分布式事务（最终一致性）")
    @GetMapping("/distributedTransaction")
    @Transactional
    public ResultView distributedTransaction() throws MQClientException {

        //封装消息
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("regionName", "region测试分布式事务" + CreateUtil.validateCode(3));
        jsonObject.put("jobName", "job测试分布式事务" + CreateUtil.validateCode(3));
        String jsonString = jsonObject.toJSONString();

        //封装消息实体
        Message msg = new Message(myConfig.getRocketmq_topic(), "tag", "key", jsonString.getBytes());
        //发送消息 用 sendMessageInTransaction
        TransactionSendResult sendResult = transactionProducer.getTransactionMQProducer().sendMessageInTransaction(msg, "test");

        if (sendResult.getSendStatus() == SendStatus.SEND_OK) {
            return ResultView.success();
        }
        return ResultView.error();
    }

    /**
     * 测试传递token到wsm-work服务中
     *
     * @return
     */
    @ApiOperation(value = "测试传递token到wsm-work服务中")
    @GetMapping("/transmitToken")
    public ResultView transmitToken() {
        ResultView resultView = workFeign.getAll_job();
        return resultView;
    }


}
