package com.study.feign;

import com.study.MyConstant;
import com.study.config.FeignRequestInterceptorConfig;
import com.study.model.JobModel;
import com.study.result.ResultView;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * feign调用wsm-work服务
 */
@FeignClient(value = MyConstant.wsm_work, fallback = WorkHystrix.class, configuration = FeignRequestInterceptorConfig.class)
public interface WorkFeign {

    @PostMapping("/job/authority_button/add")
    ResultView JobAdd(@RequestBody JobModel model);
}
