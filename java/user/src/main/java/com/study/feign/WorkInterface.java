package com.study.feign;

import com.study.currency.result.ResultView;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("wsm-work")
@RequestMapping("/work")
public interface WorkInterface {

    @GetMapping("/getById/{id}")
    ResultView getById(@PathVariable("id") Long id);
}
