package com.study.feign;

import com.study.MyConstant;
import com.study.model.JobModel;
import com.study.result.ResultView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * wsm-work服务的熔断器
 */
@Slf4j
@Component
public class WorkHystrix implements WorkFeign {

    @Override
    public ResultView getAll_job() {
        System.err.println("调用wsm-work服务getAll_job方法失败!");
        log.error("调用wsm-work服务getAll_job方法失败!");
        return ResultView.hystrixError(MyConstant.wsm_work);
    }

    @Override
    public ResultView add_job(JobModel model) {
        System.err.println("调用wsm-work服务add_job方法失败!");
        log.error("调用wsm-work服务add_job方法失败!");
        return ResultView.hystrixError(MyConstant.wsm_work);
    }
}
