package com.study.controller;

import com.study.result.ResultView;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/getName")
    public ResultView getName() {
        String name = "王帅逼";
        return ResultView.success(name);
    }
}
