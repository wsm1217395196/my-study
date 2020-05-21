package com.study.controller;

import com.netflix.appinfo.ApplicationInfoManager;
import com.netflix.appinfo.InstanceInfo;
import com.study.MyConstant;
import com.study.result.ResultView;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

/**
 * SpringCloud + Eureka 微服务优雅下线（设置服务状态，即调用setServiceStatus方法）
 */
@Data
@Slf4j
@RestController
public class EurekaServiceDownController {
    @Autowired
    private ApplicationInfoManager applicationInfoManager;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private int a = 1;

    /**
     * 设置服务状态
     * UP,DOWN,STARTING,OUT_OF_SERVICE,UNKNOWN;
     *
     * @param status
     * @return
     */
    @GetMapping("setServiceStatus")
    public ResultView setServiceStatus(String status) {
        InstanceInfo.InstanceStatus instanceStatus = InstanceInfo.InstanceStatus.toEnum(status.toUpperCase());
        applicationInfoManager.getInfo().setStatus(instanceStatus);
        log.info(MyConstant.wsm_demo + "服务设置为：" + status);
        return ResultView.success(MyConstant.wsm_demo + "服务设置为：" + status);
    }

    /**
     * 测试同步请求后，服务下线后也能正常执行
     *
     * @param sleep
     * @return
     * @throws InterruptedException
     */
    @GetMapping("/testShutdown")
    public ResultView testShutdown(Integer sleep) throws InterruptedException {
        log.info("哈哈哈");
        synchronized (this) {
            int key = getA();
            log.info(key + "请求线程睡眠" + (sleep / 1000) + "秒");
            Thread.sleep(sleep);
            stringRedisTemplate.opsForValue().set("key-" + key, "值-" + key);
            log.info(key + "请求线程结束");
            this.a++;
            return ResultView.success(key + "请求成功");
        }
    }

    @GetMapping("initA")
    public ResultView initA() {
        this.a = 1;
        return ResultView.success("初始化a=" + this.a);
    }

    @GetMapping("getA")
    public ResultView get() {
        return ResultView.success("a=" + this.a);
    }
}
