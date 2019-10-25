package com.study.controller;

import com.study.model.UserModel;
import com.study.result.PageParam;
import com.study.result.PageResult;
import com.study.result.ResultView;
import com.study.service.UserService;
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
 * 用户 前端控制器
 * </p>
 *
 * @author wsm
 * @since 2019-01-28
 */
@Api(description = "用户控制器")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "查询全部", notes = "")
    @GetMapping("/authority/getAll")
    public ResultView getAll() {
        List<UserModel> models = userService.list();
        return ResultView.success(models);
    }

    @ApiOperation(value = "分页条件查询", notes = "提交参数：{\"pageIndex\":1,\"pageSize\":10,\"sort\":\"name-desc\",\"condition\":\"{\'name\':\'\',\'nickname\':\'\',\'phone\':\'\',\'sex\':\'\',\'status\':\'\'}\"}")
    @PostMapping("/authority/getPage")
    public ResultView getPage(@RequestBody PageParam pageParam) {
        PageResult pageResult = userService.getPage(pageParam);
        return ResultView.success(pageResult);
    }

    @ApiOperation(value = "根据id查询", notes = "")
    @GetMapping("/authority/getById/{id}")
    public ResultView getById(@PathVariable Long id) {
        UserModel model = userService.getById(id);
        return ResultView.success(model);
    }

    @ApiOperation(value = "新增", notes = "")
    @PostMapping("/authority_button/add")
    public ResultView add(@RequestBody UserModel model) {
        LocalDateTime date = LocalDateTime.now();
        model.setId(CreateUtil.id());
        model.setCreateTime(date);
        model.setUpdateTime(date);
        userService.save(model);
        return ResultView.success(model);
    }

    @ApiOperation(value = "修改", notes = "")
    @PostMapping("/authority_button/update")
    public ResultView update(@RequestBody UserModel model) {
        LocalDateTime date = LocalDateTime.now();
        model.setUpdateTime(date);
        userService.updateById(model);
        return ResultView.success(model);
    }

    @ApiOperation(value = "根据id删除", notes = "")
    @DeleteMapping("/authority_button/deleteById")
    public ResultView deleteById(@RequestParam Long id) {
        userService.removeById(id);
        return ResultView.success();
    }

    @ApiOperation(value = "根据ids删除", notes = "")
    @DeleteMapping("/authority_button/deleteByIds")
    public ResultView deleteByIds(@RequestParam Long[] ids) {
        userService.removeByIds(Arrays.asList(ids));
        return ResultView.success();
    }


}

