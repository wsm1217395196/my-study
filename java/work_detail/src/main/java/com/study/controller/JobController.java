package com.study.controller;


import com.study.currency.PageParam;
import com.study.currency.PageResult;
import com.study.currency.result.ResultView;
import com.study.currency.utils.CreateUtil;
import com.study.model.JobModel;
import com.study.service.JobService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 职位表 前端控制器
 * </p>
 *
 * @author wsm123
 * @since 2019-02-28
 */
@RestController
@RequestMapping("/job")
public class JobController {

    @Autowired
    private JobService jobService;

    @ApiOperation(value = "查询全部", notes = "")
    @GetMapping("/getAll")
    public ResultView getAll() {
        List<JobModel> models = jobService.selectList(null);
        return ResultView.success(models);
    }

    @ApiOperation(value = "分页条件查询", notes = "提交参数：{\"pageIndex\":1,\"pageSize\":10,\"sort\":\"name desc\",\"condition\":\"{\'name\':\'\',\'nikename\':\'\',\'sex\':\'\',\'isEnable\':\'\'}\"}")
    @PostMapping("/getPage")
    public ResultView getPage(@RequestBody PageParam pageParam) throws JSONException {
        PageResult pageResult = jobService.getPage(pageParam);
        return ResultView.success(pageResult);
    }

    @ApiOperation(value = "根据id查询", notes = "")
    @GetMapping("/getById/{id}")
    public ResultView getById(@PathVariable Long id) {
        JobModel model = jobService.selectById(id);
        return ResultView.success(model);
    }

    @ApiOperation(value = "新增", notes = "")
    @PostMapping("/add")
    public ResultView add(JobModel model) {
        Date date = new Date();
        model.setId(CreateUtil.id());
        model.setCreateTime(date);
        model.setUpdateTime(date);
        jobService.insert(model);
        return ResultView.success(model);
    }

    @ApiOperation(value = "修改", notes = "")
    @PostMapping("/update")
    public ResultView update(JobModel model) {
        Date date = new Date();
        model.setUpdateTime(date);
        jobService.updateById(model);
        return ResultView.success(model);
    }

    @ApiOperation(value = "根据id删除", notes = "")
    @DeleteMapping("/deleteById/{id}")
    public ResultView deleteById(@PathVariable Long id) {
        jobService.deleteById(id);
        return ResultView.success();
    }

    @ApiOperation(value = "根据ids删除", notes = "")
    @DeleteMapping("/deleteByIds")
    public ResultView deleteByIds(@RequestParam Long[] ids) {
        jobService.deleteBatchIds(Arrays.asList(ids));
        return ResultView.success();
    }
}

