package com.study.controller;


import com.study.model.RegionModel;
import com.study.result.PageParam;
import com.study.result.PageResult;
import com.study.result.ResultView;
import com.study.service.RegionService;
import com.study.utils.CreateUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 域（如一个公司） 前端控制器
 * </p>
 *
 * @author wsm
 * @since 2019-07-16
 */
@Api(tags = "域控制器")
@RestController
@RequestMapping("/region")
public class RegionController {

    @Autowired
    private RegionService regionService;

    @ApiOperation(value = "查询全部", notes = "")
    @GetMapping("/getAll")
    public ResultView getAll() {
        List<RegionModel> models = regionService.getAll();
        return ResultView.success(models);
    }

    @ApiOperation(value = "根据id查询", notes = "")
    @GetMapping("/getById/{id}")
    public ResultView getById(@PathVariable Long id) {
        RegionModel model = regionService.getById(id);
        return ResultView.success(model);
    }

    @ApiOperation(value = "新增", notes = "")
    @PostMapping("/add")
    public ResultView add(@RequestBody RegionModel model) {
        model = regionService.add(model);
        return ResultView.success(model);
    }

    @ApiOperation(value = "修改", notes = "")
    @PostMapping("/update")
    public ResultView update(@RequestBody RegionModel model) {
        model = regionService.update(model);
        return ResultView.success(model);
    }

    @ApiOperation(value = "根据id删除", notes = "")
    @DeleteMapping("/deleteById")
    public ResultView deleteById(@RequestParam Long id) {
        regionService.deleteById(id);
        return ResultView.success();
    }

    @ApiOperation(value = "根据ids删除", notes = "")
    @DeleteMapping("/deleteByIds")
    public ResultView deleteByIds(@RequestParam Long[] ids) {
        regionService.deleteByIds(ids);
        return ResultView.success();
    }
}

