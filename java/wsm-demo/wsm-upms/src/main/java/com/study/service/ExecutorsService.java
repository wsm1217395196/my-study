package com.study.service;

import com.study.mapper.RegionMapper;
import com.study.model.RegionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

@Service
public class ExecutorsService {

    @Autowired
    private RegionMapper regionMapper;

    @Async("taskExecutor")
    public void testTaskExecutor(List<RegionModel> models, long startTime, CountDownLatch countDownLatch, CyclicBarrier cyclicBarrier) {
        try {
            regionBatchAdd(models, startTime);
        } finally {
            if (countDownLatch != null) {
                countDownLatch.countDown();
            }
            if (cyclicBarrier != null) {
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Transactional
    public String regionBatchAdd(List<RegionModel> models, long startTime) {
        try {
            int modelsSize = models.size();
            int addSize = 1000;
            int forSize = modelsSize / addSize;
            if (forSize > 1) {
                //循环插入1000条数据，或者1000-2000条数据之间
                int fromIndex = 0;
                int toIndex = addSize;
                for (int i = 0; i < forSize; i++) {
                    if ((modelsSize - toIndex) < addSize) {
                        toIndex = modelsSize;
                    }
                    regionMapper.batchAdd(models.subList(fromIndex, toIndex));
                    fromIndex += addSize;
                    toIndex += addSize;
                }
            } else {
                regionMapper.batchAdd(models);
            }

            String thredName = Thread.currentThread().getName();
            long entTime = System.currentTimeMillis();
            System.err.println("子线程" + thredName + ":" + (entTime - startTime) + "毫秒");
            if (new Random().nextInt(9) == 6) {
                int i = 10 / 0;
            }

            return thredName + "：" + true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
