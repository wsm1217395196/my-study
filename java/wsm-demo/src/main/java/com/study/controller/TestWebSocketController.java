package com.study.controller;

import com.study.result.ResultView;
import com.study.service.WebSoketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/testWebSocket")
public class TestWebSocketController {

    @Autowired
    private WebSoketService webSoketService;

    /**
     * 发送消息
     *
     * @return
     */
    @GetMapping("/sendMessage/{message}")
    public ResultView sendMessage(@PathVariable String message) throws IOException {
        webSoketService.sendMessage(message);
        return ResultView.success();
    }
}
