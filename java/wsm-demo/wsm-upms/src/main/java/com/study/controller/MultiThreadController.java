package com.study.controller;

import com.study.mapper.RegionMapper;
import com.study.model.RegionModel;
import com.study.result.ResultView;
import com.study.service.ExecutorsService;
import com.study.utils.CreateUtil;
import io.swagger.annotations.Api;
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
import java.util.Random;
import java.util.concurrent.*;

/**
 * 多线程处理相关
 */
@Api(tags = "多线程处理相关")
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
    private ExecutorsService executorsService;

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

            //FutureTask可用于异步获取执行结果
            List<FutureTask<String>> futureTasks = new ArrayList<>();
            List<String> threadResults = new ArrayList<>();
            for (int i = 0; i < threadSzie; i++) {
                if ((modelSize - toIndex) < addSize) {
                    toIndex = modelSize;
                }
                //执行线程任务
                FutureTask<String> futureTask = new FutureTask<>(regionBatchAddCallable(models.subList(fromIndex, toIndex), startTime));
                futureTasks.add(futureTask);
                threadPoolExecutor.submit(futureTask);
                fromIndex += addSize;
                toIndex += addSize;
            }
            //获取线程所有放回结果
            for (FutureTask<String> futureTask : futureTasks) {
                String s = futureTask.get();
                threadResults.add(s);
            }
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
            CountDownLatch countDownLatch = null;
            CyclicBarrier cyclicBarrier = null;
            if (new Random().nextBoolean() == true) {
                countDownLatch = new CountDownLatch(threadSzie);
            } else {
                cyclicBarrier = new CyclicBarrier(threadSzie);
            }

            for (int i = 0; i < threadSzie; i++) {
                if ((modelSize - toIndex) < addSize) {
                    toIndex = modelSize;
                }
                //执行线程任务
                executorsService.testTaskExecutor(models.subList(fromIndex, toIndex), startTime, countDownLatch, cyclicBarrier);
                fromIndex += addSize;
                toIndex += addSize;
            }

            if (countDownLatch != null) {
                countDownLatch.await();
                System.err.println("使用CountDownLatch：主线程等待所有子线程完成才执行。");
            }
            if (cyclicBarrier != null) {
                try {
                    cyclicBarrier.await();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
                System.err.println("使用同步屏障CyclicBarrier：主线程等待所有子线程完成才执行。");
            }
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
     * 批量添加线程Callable
     *
     * @param models
     * @param startTime
     * @return
     */
    private Callable<String> regionBatchAddCallable(List<RegionModel> models, long startTime) {
        return new Callable<String>() {
            @Override
            public String call() throws Exception {
                return executorsService.regionBatchAdd(models, startTime);
            }
        };
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
