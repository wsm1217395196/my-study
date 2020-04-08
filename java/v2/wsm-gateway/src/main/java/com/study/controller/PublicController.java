package com.study.controller;

import com.study.config.MyReactiveAuthorizationManager;
import com.study.result.ResultView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private MyReactiveAuthorizationManager myReactiveAuthorizationManager;

    /**
     * 刷新权限
     *
     * @return
     */
    @GetMapping("/loadResourceDefine")
    public ResultView loadResourceDefine() {
        myReactiveAuthorizationManager.loadResourceDefine();
        return ResultView.success();
    }
}
