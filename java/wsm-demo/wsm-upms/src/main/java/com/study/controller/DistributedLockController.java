package com.study.controller;

import com.study.config.RedisLock;
import com.study.mapper.RegionMapper;
import com.study.model.RegionModel;
import com.study.result.ResultView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 测试单机同步，分布式锁相关控制器
 * 初始化initCount存入redis中，并发请求大于等于initCount次数，看是否最后initCount等于0。
 */
@Api(tags = "测试单机同步，分布式锁相关控制器")
@RestController
@RequestMapping("/distributedLock")
public class DistributedLockController {

    public final String key = "count";//redis的key
    private final static String lock_key = "lock";

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private RedisLock redisLock;

    /**
     * 初始化initCount存入redis中
     *
     * @param initCount
     * @return
     */
    @GetMapping("/initCount")
    public ResultView initCount(int initCount) {

        Integer count = (Integer) redisTemplate.opsForValue().get(key);
        System.err.println("上次的count = " + count + "  ——  本次设置的count = " + initCount);

        redisTemplate.opsForValue().set(key, initCount);

        account = new AtomicInteger(initCount);
        return ResultView.success(initCount);
    }

//分布式的

    /**
     * 分布式锁操作（基于redis的锁）
     * 并发请求大于等于上面初始化initCount次数，看是否最后initCount等于0。
     *
     * @return
     */
    @GetMapping("/updateCount")
    public ResultView updateCount() {
        String uuid = UUID.randomUUID().toString();
        boolean lock = redisLock.lock(lock_key, uuid, 2000L);
        int count = 0;
        if (lock) {
            try {
                count = (int) redisTemplate.opsForValue().get(key);
                if (count > 0) {
                    count--;
                    redisTemplate.opsForValue().set(key, count);
                    System.err.println("count = " + count);
                    return ResultView.success();
                } else {
                    System.err.println("已减完，count = " + count);
                    return ResultView.error("已减完，count = " + count);
                }
            } finally {
                redisLock.unlock(lock_key, uuid);
            }
        } else {
            System.err.println("没有抢到锁，请求的人太多，请稍后再试！");
            return ResultView.error("没有抢到锁，请求的人太多，请稍后再试！");
        }
    }


//单机的
    /**
     * 在JavaSE5.0中新增了一个java.util.concurrent包来支持同步。ReentrantLock类是可重入、互斥、实现了Lock接口的锁，
     * 它与使用synchronized方法和块具有相同的基本行为和语义，并且扩展了其能力
     *    ReentrantLock类的常用方法有：
     *         ReentrantLock() : 创建一个ReentrantLock实例
     *         lock() : 获得锁
     *         unlock() : 释放锁
     * 注：ReentrantLock()还有一个可以创建公平锁的构造方法，但由于能大幅度降低程序运行效率，不推荐使用
     */
    private Lock lock = new ReentrantLock();
    /**
     * 原子变量实现线程同步，在java的util.concurrent.atomic包中提供了创建了原子类型变量的工具类
     * AtomicInteger(乐观锁)为例 ：
     * 以原子方式将给定值与当前值相加getAndAdd(int delta)
     * getAndDecrement()以原子方式将当前值减 1。
     * getAndIncrement()以原子方式将当前值加 1。
     * int get() : 获取当前值
     * set（）：设置给定初始值
     */
    private AtomicInteger account = null;

    /**
     * 单机操作，不同步。
     *
     * @return
     */
    @GetMapping("/updateCount1")
    public ResultView updateCount1() {
        int count = (int) redisTemplate.opsForValue().get(key);
        if (count > 0) {
            count--;
            redisTemplate.opsForValue().set(key, count);
        }
        System.err.println("count = " + count);
        return ResultView.success(count);
    }

    /**
     * 单机操作（同步方法），实现同步。
     *
     * @return
     */
    @GetMapping("/updateCount2")
    public synchronized ResultView updateCount2() {
        int count = (int) redisTemplate.opsForValue().get(key);
        if (count > 0) {
            count--;
            redisTemplate.opsForValue().set(key, count);
        }
        System.err.println("count = " + count);
        return ResultView.success(count);
    }

    /**
     * 单机操作（同步代码块），实现同步。
     *
     * @return
     */
    @GetMapping("/updateCount3")
    public ResultView updateCount3() {
        int count;
        synchronized (this) {
            count = (int) redisTemplate.opsForValue().get(key);
            if (count > 0) {
                count--;
                redisTemplate.opsForValue().set(key, count);
            }
        }
        System.err.println("count = " + count);
        return ResultView.success(count);
    }

    /**
     * 单机操作（重入锁），实现同步。
     *
     * @return
     */
    @GetMapping("/updateCount4")
    public ResultView updateCount4() {
        lock.lock();//上锁
        int count;
        try {
            count = (int) redisTemplate.opsForValue().get(key);
            if (count > 0) {
                count--;
                redisTemplate.opsForValue().set(key, count);
            }
        } finally {
            lock.unlock();//解锁
        }
        System.err.println("count = " + count);
        return ResultView.success(count);
    }

    /**
     * 单机操作，原子变量实现线程同步，AtomicInteger(乐观锁)为例。
     *
     * @return
     */
    @GetMapping("/updateCount5")
    public ResultView updateCount5() {
        int count = account.get();
        if (count > 0) {
            account.getAndDecrement();//只会对这操作同步
            count = account.get();
//            redisTemplate.opsForValue().set(key, count);//这里不能同步
        }
        System.err.println("count = " + count);
        return ResultView.success(count);
    }


    @Autowired
    private RegionMapper regionMapper;
    private Long id = 520l;

    /**
     * 测试更新锁(初始化)
     */
    @ApiOperation("测试更新锁(初始化)")
    @GetMapping("/testUpdateLockInit")
    public ResultView testUpdateLockInit() {
        regionMapper.deleteById(id);
        RegionModel model = new RegionModel();
        model.setId(id);
        model.setName(id + "");
        model.setCode(id + "");
        model.setIsEnabled(0);
        regionMapper.insert(model);
        return ResultView.success();
    }

    /**
     * 测试更新锁
     */
    @ApiOperation("测试更新锁")
    @GetMapping("/testUpdateLock")
    @Transactional
    public ResultView testUpdateLock(@RequestParam Integer addNum) {
        int count = regionMapper.testUpdateLock(id, addNum);
        if (count == 0) {
            System.err.println("更新失败");
            return ResultView.success("更新失败");
        }
        return ResultView.success();
    }

}
