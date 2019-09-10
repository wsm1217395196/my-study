package com.study.service;

import com.alibaba.fastjson.JSONObject;
import com.study.config.MyConfig;
import com.study.model.JobModel;
import com.study.utils.CreateUtil;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 分布式事务RocketMQ 消费者
 * 消费端跟之前普通消费没区别
 * 因为分布式事务主要是通过 生产端控制 消息的发送
 */
@Component
public class TransactionConsumer {

    /**
     * 事务消费者
     */
    private DefaultMQPushConsumer consumer;

    public TransactionConsumer(@Autowired MyConfig myConfig, @Autowired JobService jobService) throws MQClientException {
        // 设置消费组
        consumer = new DefaultMQPushConsumer(myConfig.getRocketmq_Consumer());
        // 添加rocketmq服务器地址
        consumer.setNamesrvAddr(myConfig.getRocketmq_namesrvAddr());
        // 设置订阅主题 "*"代表订阅全部
        consumer.subscribe(myConfig.getRocketmq_topic(), "*");
        // 监听消息 1、MessageListenerConcurrently异步 2、MessageListenerOrderly同步
        consumer.registerMessageListener((MessageListenerConcurrently) (msgs, context) -> {

            // 解析消息
            MessageExt messageExt = msgs.get(0);
            String msg = new String(messageExt.getBody());
            JSONObject jsonObject = JSONObject.parseObject(msg);
            String jobName = jsonObject.getString("jobName");

            JobModel jobModel = new JobModel();
            jobModel.setId(CreateUtil.id());
            jobModel.setName(jobName);

            try {
//                int i = 10 / 0;
                // 插入数据
                jobService.insert(jobModel);

//                1、MessageListenerConcurrently
                System.err.println("分布式事务消费端：消费成功");
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS; // 消费成功

//                2、MessageListenerOrderly
//                System.err.println("分布式事务消费端：成功");
//                return ConsumeOrderlyStatus.SUCCESS;//成功
            } catch (Exception e) {
                e.printStackTrace();

//                1、MessageListenerConcurrently
                System.err.println("分布式事务消费端： 消费失败稍后再确认重试！");
                return ConsumeConcurrentlyStatus.RECONSUME_LATER;

//                2、MessageListenerOrderly
//                System.err.println("分布式事务消费端： rollback失败回滚！");
//                return ConsumeOrderlyStatus.ROLLBACK;//rollback 失败回滚

//                System.err.println("分布式事务消费端：暂停队列");
//                return ConsumeOrderlyStatus.SUSPEND_CURRENT_QUEUE_A_MOMENT;//暂停队列
            }
        });
        consumer.start();
    }
}
