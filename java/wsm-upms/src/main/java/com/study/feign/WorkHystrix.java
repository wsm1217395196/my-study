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
    public ResultView JobAdd(JobModel model) {
        System.err.println("调用wsm-work服务JobAdd方法失败!");
        logger.error("调用wsm-work服务JobAdd方法失败!");
        return ResultView.hystrixError(MyConstant.wsm_work);
    }
}
