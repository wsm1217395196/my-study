package com.study.feign;

import com.study.MyConstant;
import com.study.model.JobModel;
import com.study.result.ResultView;
import org.springframework.stereotype.Component;

/**
 * wsm-work服务的熔断器
 */
@Component
public class WorkHystrix implements WorkFeign {

    @Override
    public ResultView JobAdd(JobModel model) {
        return ResultView.hystrixError(MyConstant.wsm_work);
    }
}
