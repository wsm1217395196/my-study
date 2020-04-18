package com.study.controller;


import com.study.model.JobModel;
import com.study.result.PageParam;
import com.study.result.PageResult;
import com.study.result.ResultView;
import com.study.service.JobService;
import com.study.utils.CreateUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

/**
 * <p>
 * 职位表 前端控制器
 * </p>
 *
 * @author wsm123
 * @since 2019-02-28
 */
@Api(tags = "职位控制器")
@RestController
@RequestMapping("/job")
public class JobController {

    @Autowired
    private JobService jobService;

    @ApiOperation(value = "测试查询时间段", notes = "")
    @GetMapping("/test")
    public ResultView test() {
        List<JobModel> models = jobService.test();
        return ResultView.success(models);
    }

    @ApiOperation(value = "查询全部", notes = "")
    @GetMapping("/authority/getAll")
    public ResultView getAll(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        System.err.println(authorization);
        List<JobModel> models = jobService.list();
        return ResultView.success(models);
    }

    @ApiOperation(value = "分页条件查询", notes = "提交参数：{\"pageIndex\":1,\"pageSize\":10,\"sort\":\"name-desc\",\"condition\":\"{\'name\':\'\',\'isEnabled\':\'\'}\"}")
    @PostMapping("/authority/getPage")
    public ResultView getPage(@RequestBody PageParam pageParam) {
        PageResult pageResult = jobService.getPage(pageParam);
        return ResultView.success(pageResult);
    }

    @ApiOperation(value = "根据id查询", notes = "")
    @GetMapping("/authority/getById/{id}")
    public ResultView getById(@PathVariable Long id) {
        JobModel model = jobService.getById(id);
        return ResultView.success(model);
    }

    @ApiOperation(value = "新增", notes = "")
    @PostMapping("/authority_button/add")
    public ResultView add(@RequestBody JobModel model, HttpServletRequest request) {
        Enumeration<String> headerNames = request.getHeaderNames();
        LocalDateTime date = LocalDateTime.now();
        model.setId(CreateUtil.id());
        model.setCreateTime(date);
        model.setUpdateTime(date);
        jobService.save(model);

//        int i = 10 / 0;

        return ResultView.success();
    }

    @ApiOperation(value = "修改", notes = "")
    @PostMapping("/authority_button/update")
    public ResultView update(@RequestBody JobModel model) {
        LocalDateTime date = LocalDateTime.now();
        model.setUpdateTime(date);
        jobService.updateById(model);
        return ResultView.success(model);
    }

    @ApiOperation(value = "根据id删除", notes = "")
    @DeleteMapping("/authority_button/authority_button/deleteById/{id}")
    public ResultView deleteById(@PathVariable Long id) {
        jobService.removeById(id);
        return ResultView.success();
    }

    @ApiOperation(value = "根据ids删除", notes = "")
    @DeleteMapping("/authority_button/deleteByIds")
    public ResultView deleteByIds(@RequestParam Long[] ids) {
        jobService.removeByIds(Arrays.asList(ids));
        return ResultView.success();
    }
}

