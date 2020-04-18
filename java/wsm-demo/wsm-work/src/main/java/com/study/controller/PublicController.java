package com.study.controller;

import com.study.config.MySecurityMetadataSource;
import com.study.result.ResultView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 公共控制器
 */
@Api(tags = "公共控制器")
@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private MySecurityMetadataSource mySecurityMetadataSource;

    @ApiOperation(value = "刷新本服务权限")
    @GetMapping("/loadResourceDefine")
    public ResultView loadResourceDefine() {
        mySecurityMetadataSource.loadResourceDefine();
        return ResultView.success();
    }
}
