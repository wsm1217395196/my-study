package com.study.controller;

import com.study.mapper.RegionMapper;
import com.study.model.RegionModel;
import com.study.result.ResultView;
import com.study.service.ExecutorService;
import com.study.utils.CreateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * 多线程处理相关
 */
@RestController
@RequestMapping("MultiThread")
public class MultiThreadController {

    @Autowired
    private RegionMapper regionMapper;
    @Autowired
    private TaskExecutor taskExecutor;
    @Autowired
    private ThreadPoolExecutor threadPoolExecutor;
    @Autowired
    private ExecutorService executorService;

    /**
     * 使用java threadPoolExecutor线程池 插入数据 异常会回滚 有返回结果
     * 用这个
     *
     * @param modelSize  插入数据数量
     * @param threadSzie 线程数
     * @return
     * @throws InterruptedException
     */
    @Transactional
    @RequestMapping("/testThreadPoolExecutor")
    public ResultView testThreadPoolExecutor(@RequestParam int modelSize, @RequestParam int threadSzie) throws Exception {
        long startTime = System.currentTimeMillis();
        List<RegionModel> models = getRegionModels(modelSize);

        if (modelSize > 5000) {
            //使用多线程 每个线程插入数据为addSize,或者addSize-2*addSize之间
            int fromIndex = 0;
            int addSize = modelSize / threadSzie;
            int toIndex = addSize;
            //1、等待多线程完成的CountDownLatch。2、或者用同步屏障CyclicBarrier也可以。
            CountDownLatch countDownLatch = new CountDownLatch(threadSzie);
            List<Object> threadResults = new ArrayList<>();
            for (int i = 0; i < threadSzie; i++) {
                if ((modelSize - toIndex) < addSize) {
                    toIndex = modelSize;
                }
                //执行线程任务
                Future threadResult = threadPoolExecutor.submit(new MyThread(models.subList(fromIndex, toIndex), startTime, countDownLatch, executorService));
                threadResults.add(threadResult.get());
                fromIndex += addSize;
                toIndex += addSize;
            }
            countDownLatch.await();
            System.err.println("所有线程结果：" + threadResults);
        } else {
            //使用单线程
            regionMapper.batchAdd(models);
        }
//        int i = 10 / 0;
        long entTime = System.currentTimeMillis();
        System.err.println("主线程为：" + (entTime - startTime) + "毫秒");
        return ResultView.success("主线程为：" + (entTime - startTime) + "毫秒");
    }

    /**
     * 使用springboot ThreadPoolTaskExecutor线程池（插入数据）异常不回滚，不返回结果。
     * 注：可以用注解。可以和定时任务一起使用。
     *
     * @param modelSize  插入数据数量
     * @param threadSzie 线程数
     * @return
     * @throws InterruptedException
     */
    @Transactional
    @RequestMapping("/testTaskExecutor")
    public ResultView testTaskExecutor(@RequestParam int modelSize, @RequestParam int threadSzie) throws InterruptedException {
        long startTime = System.currentTimeMillis();
        List<RegionModel> models = getRegionModels(modelSize);

        if (modelSize > 5000) {
            //使用多线程 每个线程插入数据为addSize,或者addSize-2*addSize之间
            int fromIndex = 0;
            int addSize = modelSize / threadSzie;
            int toIndex = addSize;
            //1、等待多线程完成的CountDownLatch。2、或者用同步屏障CyclicBarrier也可以。
            CountDownLatch countDownLatch = new CountDownLatch(threadSzie);
            for (int i = 0; i < threadSzie; i++) {
                if ((modelSize - toIndex) < addSize) {
                    toIndex = modelSize;
                }
                //执行线程任务
                executorService.testTaskExecutor(models.subList(fromIndex, toIndex), startTime, countDownLatch);
                fromIndex += addSize;
                toIndex += addSize;
            }
            countDownLatch.await();
        } else {
            //使用单线程
            regionMapper.batchAdd(models);
        }
        long entTime = System.currentTimeMillis();
        System.err.println("主线程为：" + (entTime - startTime) + "毫秒");
        return ResultView.success("主线程为：" + (entTime - startTime) + "毫秒");
    }


    /**
     * 单线程 插入数据
     *
     * @param modelSize 全部插入数据数量
     * @return
     */
    @GetMapping("/testSingleThread")
    @Transactional
    public ResultView testSingleThread(@RequestParam int modelSize) {
        long startTime = System.currentTimeMillis();
        List<RegionModel> models = getRegionModels(modelSize);

        regionMapper.batchAdd(models);

        long entTime = System.currentTimeMillis();
        System.err.println("单线程为：" + (entTime - startTime) + "毫秒");
        return ResultView.success("单线程为：" + (entTime - startTime) + "毫秒");
    }


    /**
     * 多线程 实现Callable 插入数据 异常会回滚 有返回结果
     * 注：好像是同一线程跑的
     *
     * @param modelSize  插入数据数量
     * @param threadSzie 线程数
     * @return
     */
    @GetMapping("/testCallable")
    @Transactional
    public ResultView testCallable(@RequestParam int modelSize, @RequestParam int threadSzie) throws Exception {
        long startTime = System.currentTimeMillis();
        List<RegionModel> models = getRegionModels(modelSize);

        if (modelSize > 5000) {
            //使用多线程 每个线程插入数据为addSize,或者addSize-2*addSize之间
            int fromIndex = 0;
            int addSize = modelSize / threadSzie;
            int toIndex = addSize;
            //1、等待多线程完成的CountDownLatch。2、或者用同步屏障CyclicBarrier也可以。
            CountDownLatch countDownLatch = new CountDownLatch(threadSzie);
            List<Object> threadResults = new ArrayList<>();
            for (int i = 0; i < threadSzie; i++) {
                if ((modelSize - toIndex) < addSize) {
                    toIndex = modelSize;
                }
                MyThread myThread = new MyThread(models.subList(fromIndex, toIndex), startTime, countDownLatch, executorService);
                Object threadResult = myThread.call();//执行线程任务
                threadResults.add(threadResult);
                fromIndex += addSize;
                toIndex += addSize;
            }
            countDownLatch.await();
            System.err.println("所有线程结果：" + threadResults);
        } else {
            //使用单线程
            regionMapper.batchAdd(models);
        }
        long entTime = System.currentTimeMillis();
        System.err.println("主线程为：" + (entTime - startTime) + "毫秒");
        return ResultView.success("主线程为：" + (entTime - startTime) + "毫秒");
    }

    /**
     * 实现Callable的线程
     */
    class MyThread implements Callable {
        List<RegionModel> models;
        long startTime;
        private CountDownLatch countDownLatch;
        private ExecutorService executorService;

        public MyThread(List<RegionModel> models, long startTime, CountDownLatch countDownLatch, @Autowired ExecutorService executorService) {
            this.models = models;
            this.startTime = startTime;
            this.countDownLatch = countDownLatch;
            this.executorService = executorService;
        }

        @Override
        public Object call() throws Exception {
            return executorService.regionBatchAdd(models, startTime, countDownLatch);
        }
    }

    /**
     * 创建model数据
     *
     * @param modelSize
     * @return
     */
    private List<RegionModel> getRegionModels(int modelSize) {
        List<RegionModel> models = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();
        for (int i = 0; i < modelSize; i++) {
            RegionModel model = new RegionModel();
            Long id = CreateUtil.id();
            model.setId(id);
            model.setName("域" + id);
            model.setCode(id.toString());
            model.setParentId(1L);
            model.setCreateBy("wsm");
            model.setCreateTime(now);
            models.add(model);
        }
        return models;
    }
}
