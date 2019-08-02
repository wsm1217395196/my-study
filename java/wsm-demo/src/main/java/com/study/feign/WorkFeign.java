package com.study.feign;

import com.study.result.ResultView;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "wsm-work",fallback = WorkHystrix.class)
public interface WorkFeign {

    @GetMapping("/work/getById/{id}")
    ResultView getById(@PathVariable("id") Long id);
}
