package com.study.service;

import com.alibaba.fastjson.JSONObject;
import com.study.config.MyConfig;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.*;

/**
 * 分布式事务RocketMQ 生产者
 */
@Component
public class TransactionProducer {

    /**
     * 自定义事务监听器 用于 事务的二次确认 和 事务回查
     */
    private TransactionListener transactionListener;

    /**
     * 事务生产者
     */
    private TransactionMQProducer transactionMQProducer = null;

    /**
     * 官方建议自定义线程 给线程取自定义名称 发现问题更好排查
     */
    private ExecutorService executorService = new ThreadPoolExecutor(2, 5, 100,
            TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(2000), new ThreadFactory() {
        @Override
        public Thread newThread(Runnable r) {
            Thread thread = new Thread(r);
            thread.setName("test-transaction");
            return thread;
        }
    });

    public TransactionProducer(@Autowired MyConfig myConfig, @Autowired RegionService regionService) {
        transactionListener = new TransactionListenerImpl(regionService);
        // 初始化 事务生产者
        transactionMQProducer = new TransactionMQProducer(myConfig.getRocketmq_producer());
        // 添加rocketmq服务器地址
        transactionMQProducer.setNamesrvAddr(myConfig.getRocketmq_namesrvAddr());
        // 添加事务监听器
        transactionMQProducer.setTransactionListener(transactionListener);
        // 添加自定义线程池
        transactionMQProducer.setExecutorService(executorService);
        start();
    }

    public void start() {
        try {
            this.transactionMQProducer.start();
        } catch (MQClientException e) {
            e.printStackTrace();
        }
    }

    public void shutdown() {
        this.transactionMQProducer.shutdown();
    }

    public TransactionMQProducer getTransactionMQProducer() {
        return this.transactionMQProducer;
    }

}

/**
 * 自定义事务监听器
 */
class TransactionListenerImpl implements TransactionListener {

    @Autowired
    private RegionService regionService;

    public TransactionListenerImpl(RegionService regionService) {
        this.regionService = regionService;
    }

    @Override
    public LocalTransactionState executeLocalTransaction(Message message, Object o) {
        // 解析消息
        String msg = new String(message.getBody());
        JSONObject jsonObject = JSONObject.parseObject(msg);
        String regionName = jsonObject.getString("regionName");

        /**
         * 本地事务执行会有三种可能
         * 1、commit 成功
         * 2、网络等原因服务宕机收不到返回结果
         * 3、Rollback 失败
         */
        // 插入数据
        boolean result = regionService.distributedTransaction(regionName);

        if (result) {
            // 1、二次确认消息，然后消费者可以消费
//            System.err.println("commit 成功");
//            return LocalTransactionState.COMMIT_MESSAGE;

            // 2、Broker端会进行回查消息
            System.err.println("unknow 中间状态");
            return LocalTransactionState.UNKNOW;
        } else {
            // 3、回滚消息，Broker端会删除半消息
            System.err.println("rollback 失败");
            return LocalTransactionState.ROLLBACK_MESSAGE;
        }
    }

    /**
     * 只有上面接口返回 LocalTransactionState.UNKNOW 才会调用这个
     *
     * @param msg 消息
     * @return
     */
    @Override
    public LocalTransactionState checkLocalTransaction(MessageExt msg) {
        String key = msg.getKeys();
        System.err.println("进入了unknow 中间状态——key:" + key);
        //TODO 1、必须根据key先去检查本地事务消息是否完成。
        /**
         * 因为有种情况就是：上面本地事务执行成功了，但是return LocalTransactionState.COMMIT_MESSAG的时候
         * 服务挂了，那么最终 Brock还未收到消息的二次确定，还是个半消息 ，所以当重新启动的时候还是回调这个回调接口。
         * 如果不先查询上面本地事务的执行情况 直接在执行本地事务，那么就相当于成功执行了两次本地事务了。
         */
        // TODO 2、这里返回要么commit 要么rollback。没有必要在返回 UNKNOW
        return LocalTransactionState.COMMIT_MESSAGE;
    }
}
