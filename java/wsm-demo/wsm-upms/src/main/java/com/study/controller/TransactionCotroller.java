package com.study.controller;

import com.alibaba.fastjson.JSONObject;
import com.study.MyConstant;
import com.study.config.MyConfig;
import com.study.exception.MyRuntimeException;
import com.study.feign.WorkFeign;
import com.study.model.JobModel;
import com.study.model.RegionModel;
import com.study.result.ResultView;
import com.study.service.RegionService;
import com.study.service.TransactionProducer;
import com.study.utils.CreateUtil;
import io.seata.spring.annotation.GlobalTransactional;
import io.swagger.annotations.Api;
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
 * 分布式事务相关控制器
 */
@Api(tags = "测试控制器")
@RestController
@RequestMapping("/test")
public class TransactionCotroller {

    @Autowired
    private MyConfig myConfig;
    @Autowired
    private TransactionProducer transactionProducer;
    @Autowired
    private WorkFeign workFeign;
    @Autowired
    private RegionService regionService;

    /**
     * 测试rocketmq分布式事务
     * 利用消息队列的特性：生产的消息一定会被消费，从而达到最终一致性
     *
     * @return
     */
    @ApiOperation(value = "测试rocketmq分布式事务（最终一致性）")
    @GetMapping("/testRocketmqTransaction")
    @Transactional
    public ResultView testRocketmqTransaction() throws MQClientException {

        //封装消息
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("regionName", "（rocketmq）region测试分布式事务" + CreateUtil.validateCode(3));
        jsonObject.put("jobName", "（rocketmq）job测试分布式事务" + CreateUtil.validateCode(3));
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
     * 测试阿里seata分布式事务
     *
     * @return
     */
    @ApiOperation(value = "测试阿里seata分布式事务")
    @GlobalTransactional
    @GetMapping("/testSeataTransaction")
    public ResultView testSeataTransaction() {

        RegionModel regionModel = new RegionModel();
        regionModel.setId(CreateUtil.id());
        regionModel.setName("（seata）region测试分布式事务" + CreateUtil.validateCode(3));
        regionService.save(regionModel);

        JobModel jobModel = new JobModel();
        jobModel.setId(CreateUtil.id());
        jobModel.setName("（seata）job测试分布式事务" + CreateUtil.validateCode(3));
        ResultView resultView = workFeign.add_job(jobModel);

        if (resultView.getCode() != MyConstant.One) {
            throw new MyRuntimeException(resultView);
        }

        int i = 10 / 0;

        return resultView;
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
