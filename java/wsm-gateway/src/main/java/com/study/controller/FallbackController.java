package com.study.controller;

import com.study.result.ResultEnum;
import com.study.result.ResultView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 通用熔断器
 */
@RestController
@RequestMapping("common")
public class FallbackController {

    Logger logger = LoggerFactory.getLogger(FallbackController.class);

    @RequestMapping("fallback")
    public ResultView fallback() {
        ResultView resultView = ResultView.error(ResultEnum.CODE_504);
        logger.error(resultView.toString());
        return resultView;
    }
}
