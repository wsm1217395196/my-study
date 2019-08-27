package com.study.feign;

import com.study.MyConstant;
import com.study.model.JobModel;
import com.study.result.ResultView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * wsm-work服务的熔断器
 */
@Component
public class WorkHystrix implements WorkFeign {

    private final Logger logger = LoggerFactory.getLogger(OauthHystrix.class);

    @Override
    public ResultView getAll_job() {
        System.err.println("调用wsm-work服务getAll_job方法失败!");
        logger.error("调用wsm-work服务getAll_job方法失败!");
        return ResultView.hystrixError(MyConstant.wsm_work);
    }

    @Override
    public ResultView add_job(JobModel model) {

//        lcn回滚
//        DTXUserControls.rollbackGroup(TracingContext.tracing().groupId());
//        System.out.println("lcn组：" + TracingContext.tracing().groupId());

        System.err.println("调用wsm-work服务add_job方法失败!");
        logger.error("调用wsm-work服务add_job方法失败!");
        return ResultView.hystrixError(MyConstant.wsm_work);
    }
}
