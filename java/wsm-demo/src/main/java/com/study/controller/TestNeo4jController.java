package com.study.controller;

import com.study.mapper.UserNeo4jMapper;
import com.study.model.UserNeo4jModel;
import com.study.result.ResultView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequestMapping("/testNeo4j")
@RestController
public class TestNeo4jController {

    @Autowired
    private UserNeo4jMapper userNeo4jMapper;

    @GetMapping("/getAll")
    public ResultView getAll() {
        Iterable<UserNeo4jModel> models = userNeo4jMapper.findAll();
        return ResultView.success(models);
    }

    @GetMapping("/getById/{id}")
    public ResultView getById(@PathVariable Long id){
        Optional<UserNeo4jModel> optional = userNeo4jMapper.findById(id);
        return ResultView.success(optional);
    }

    @GetMapping("/getByName/{name}")
    public ResultView getByName(@PathVariable String name){
        UserNeo4jModel model = userNeo4jMapper.getByName(name);
        return ResultView.success(model);
    }

    @PostMapping("/save")
    public ResultView save(@RequestBody UserNeo4jModel model) {
        userNeo4jMapper.save(model);
        return ResultView.success();
    }

    @GetMapping("/getPage")
    public ResultView getPage(){
        userNeo4jMapper.findAll();
//        userNeo4jMapper.
        return  ResultView.success();
    }

    @DeleteMapping("/deleteById/{id}")
    public ResultView deleteById(@PathVariable Long id){
        userNeo4jMapper.deleteById(id);
        return ResultView.success();
    }

}
