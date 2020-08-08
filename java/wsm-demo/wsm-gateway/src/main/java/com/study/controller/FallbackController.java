package com.study.controller;

import com.study.result.ResultEnum;
import com.study.result.ResultView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 通用熔断器
 */
@Slf4j
@RestController
@RequestMapping("common")
public class FallbackController {

    @RequestMapping("fallback")
    public ResultView fallback() {
        ResultView resultView = ResultView.error(ResultEnum.CODE_504);
        log.error(resultView.toString());
        return resultView;
    }
}
