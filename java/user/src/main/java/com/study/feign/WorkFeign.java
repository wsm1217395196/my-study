package com.study.feign;

import com.study.currency.result.ResultView;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "wsm-work",fallback = WorkHystrix.class)
public interface WorkFeign {

//    @RequestMapping(value = "/work/getById/{id}",method = RequestMethod.GET)
    @GetMapping("/work/getById/{id}")
    ResultView getById(@PathVariable("id") Long id);
}
