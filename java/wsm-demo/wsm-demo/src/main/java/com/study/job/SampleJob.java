package com.study.job;

import com.study.exception.MyRuntimeException;
import com.study.model.RegionModel;
import com.study.service.RegionService;
import com.study.utils.DateUtil;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Random;

@Slf4j
@Component
public class SampleJob {

    Random random = new Random();
    @Autowired
    private RegionService regionService;

    @XxlJob("sampleJob1")
    public ReturnT<String> sampleJob1(String s) {
        System.err.println("当前时间：" + DateUtil.formatDate(new Date()));
        ReturnT<String> success = ReturnT.SUCCESS;
        try {
            List<RegionModel> models = regionService.getAll();
            System.out.println("models = " + models);
            int i = random.nextInt(5);
            if (i == 3) {
                throw new MyRuntimeException("任务调度错误！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            ReturnT returnT = new ReturnT();
//            returnT.setCode(666);
            returnT.setCode(ReturnT.FAIL_CODE);
            returnT.setMsg(e.getMessage());
            success = returnT;
        }
        return success;
    }
}
