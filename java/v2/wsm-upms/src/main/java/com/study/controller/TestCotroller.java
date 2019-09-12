package com.study.controller;

import com.alibaba.fastjson.JSONObject;
import com.study.MyConstant;
import com.study.config.MyConfig;
import com.study.exception.MyRuntimeException;
import com.study.feign.WorkFeign;
import com.study.mapper.RegionMapper;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 测试控制器
 */
@Api(description = "测试控制器")
@RestController
@RequestMapping("/test")
public class TestCotroller {

    @Autowired
    private MyConfig myConfig;
    @Autowired
    private TransactionProducer transactionProducer;
    @Autowired
    private WorkFeign workFeign;
    @Autowired
    private RegionService regionService;
    @Autowired
    private RegionMapper regionMapper;


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
        regionService.insert(regionModel);

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


    /**
     * 单线程插入
     *
     * @param modelSize
     * @return
     */
    @GetMapping("/testSinglehread")
    @Transactional
    public ResultView testSinglehread(@RequestParam int modelSize) {
        long startTime = System.currentTimeMillis();
        List<RegionModel> models = new ArrayList<>();
        for (int i = -1; i < modelSize; i++) {
            if (i == 1) {
                continue;
            }
            RegionModel model = new RegionModel();
            model.setId(Long.valueOf(Integer.toString(i)));
            model.setName("域" + i);
            model.setCode("code" + i);
            model.setParentId(1L);
            model.setCreateBy("wsm");
            model.setCreateTime(new Date());
            models.add(model);
        }
//        regionService.insertBatch(models);
        regionMapper.batchAdd(models);
        long entTime = System.currentTimeMillis();
        return ResultView.success("单线程为：" + (entTime - startTime) + "毫秒");
    }


    /**
     * 多线程插入
     *
     * @param modelSize
     * @param threadSzie
     * @return
     */
    @GetMapping("/testMultithread")
    @Transactional
    public ResultView testMultithreading(@RequestParam int modelSize, @RequestParam int threadSzie) {
        long startTime = System.currentTimeMillis();
        int size1 = -1;
        int size2 = modelSize / threadSzie;
        for (int i = 0; i < threadSzie; i++) {
            String thredName = "线程" + i;
            MyThread myThread = new MyThread(startTime, thredName, size1, size2, regionMapper);
            myThread.start();

            size1 = size2;
            size2 += modelSize / threadSzie;
        }
        long entTime = System.currentTimeMillis();
        return ResultView.success("多线程为：" + (entTime - startTime) + "毫秒");
    }

    class MyThread extends Thread {
        long startTime;
        private int size1;
        private int size2;
        private RegionMapper regionMapper;
        private String thredName1;

        public MyThread(long startTime, String thredName, int size1, int size2, @Autowired RegionMapper regionMapper) {
            this.startTime = startTime;
            this.thredName1 = thredName;
            this.size1 = size1;
            this.size2 = size2;
            this.regionMapper = regionMapper;
            this.setName(thredName);
        }

        @Override
        public void run() {
            List<RegionModel> models = new ArrayList<>();
            for (int i = size1; i < size2; i++) {
                if (i == 1) {
                    continue;
                }
//                if (i == 8) {
//                    int b = 10 / 0;
//                }
                RegionModel model = new RegionModel();
                model.setId(Long.valueOf(Integer.toString(i)));
                model.setName("域" + i);
                model.setCode("code" + i);
                model.setParentId(1L);
                model.setCreateBy("wsm");
                model.setCreateTime(new Date());
                models.add(model);
            }
            int i = regionMapper.batchAdd(models);
            if (i == models.size()) {
                long entTime = System.currentTimeMillis();
                System.err.println(thredName1 + ":" + (entTime - startTime) + "毫秒");
            }

        }
    }

}
