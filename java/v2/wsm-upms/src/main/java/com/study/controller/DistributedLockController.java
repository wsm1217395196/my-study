package com.study.controller;

import com.study.result.ResultView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 测试单机同步，分布式锁相关控制器
 * 初始化initCount存入redis中，并发请求大于等于initCount次数，看是否最后initCount等于0。
 */
@RestController
@RequestMapping("/distributedLock")
public class DistributedLockController {

    public final String key = "count";//redis的key

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

    //分布式的
    private final static String LOCK_ID = "wsm";

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 获得锁
     */
    public boolean getLock(String lockId, long millisecond) {
        Boolean success = redisTemplate.opsForValue().setIfAbsent(lockId, "lock",
                millisecond, TimeUnit.MILLISECONDS);
        return success != null && success;
    }

    /**
     * 释放锁
     *
     * @param lockId
     */
    public void releaseLock(String lockId) {
        redisTemplate.delete(lockId);
    }

    /**
     * 初始化initCount存入redis中
     *
     * @param initCount
     * @return
     */
    @GetMapping("/setCount")
    public ResultView setCount(int initCount) {

        int count = (int) redisTemplate.opsForValue().get(key);
        System.err.println("上次的count = " + count + "  ——  本次设置的count = " + initCount);

        redisTemplate.opsForValue().set(key, initCount);

        account = new AtomicInteger(initCount);

        return ResultView.success(initCount);
    }

    /**
     * 分布式锁操作（基于redis的锁）
     * 并发请求大于等于上面初始化initCount次数，看是否最后initCount等于0。
     *
     * @return
     */
    @GetMapping("/updateCount6")
    public ResultView updateCount6() {
        boolean lock = getLock(LOCK_ID, 2000);
        int count = 0;
        if (lock) {
            count = (int) redisTemplate.opsForValue().get(key);
            if (count > 0) {
                count--;
                redisTemplate.opsForValue().set(key, count);
            }
            releaseLock(LOCK_ID);
            System.err.println("count = " + count);
        } else {
            System.err.println("没有抢到锁！");
        }
        return ResultView.success(count);
    }

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

}
