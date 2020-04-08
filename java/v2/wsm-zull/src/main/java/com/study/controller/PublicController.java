package com.study.controller;

import com.study.config.MySecurityMetadataSource;
import com.study.result.ResultView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private MySecurityMetadataSource mySecurityMetadataSource;

    /**
     * 刷新权限
     *
     * @return
     */
    @GetMapping("/loadResourceDefine")
    public ResultView loadResourceDefine() {
        mySecurityMetadataSource.loadResourceDefine();
        return ResultView.success();
    }
}
