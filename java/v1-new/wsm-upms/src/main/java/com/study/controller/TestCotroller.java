package com.study.controller;

import com.study.feign.WorkFeign;
import com.study.result.ResultView;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试控制器
 */
@RestController
@RequestMapping("/test")
public class TestCotroller {

    @Autowired
    private WorkFeign workFeign;

    /**
     * 测试传递token到wsm-work服务中
     *
     * @return
     */
    @ApiOperation(value = "测试传递token到wsm-work服务中")
    @GetMapping("/transmitToken")
    public ResultView transmitToken() {
        ResultView resultView = workFeign.getAll_job();
        return resultView;
    }
}
