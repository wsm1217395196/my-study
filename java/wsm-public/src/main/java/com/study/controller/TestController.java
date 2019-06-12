package com.study.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试相关控制器
 */
@Api(description = "测试相关控制器")
@RestController
@RequestMapping("/test")
public class TestController {

    @Value("${wsm.name}")
    private String wsmName;

    @Value("${wsm.age}")
    private Integer wsmAge;

    @ApiOperation("测试读取git配置中的值")
    @GetMapping("/getGitConfigValue")
    public String getGitConfigValue() {
        System.out.println(wsmName);
        System.out.println(wsmAge);
        return wsmName;
    }
}
