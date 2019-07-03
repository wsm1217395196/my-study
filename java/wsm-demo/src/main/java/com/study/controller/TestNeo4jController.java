package com.study.controller;

import com.study.mapper.UserNeo4jMapper;
import com.study.model.UserNeo4jModel;
import com.study.result.ResultView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/testNeo4j")
@RestController
public class TestNeo4jController {

    @Autowired
    private UserNeo4jMapper userNeo4jMapper;

    @GetMapping("/getAll")
    public ResultView getAll() {
        Iterable<UserNeo4jModel> all = userNeo4jMapper.findAll();
        return ResultView.success(all);
    }
}
