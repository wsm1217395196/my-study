package com.study.controller;

import com.study.dto.UserAddDTO;
import com.study.model.UserModel;
import com.study.result.PageParam;
import com.study.result.PageResult;
import com.study.result.ResultView;
import com.study.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
@Api(tags = "用户控制器")
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

    @ApiOperation(value = "查询全部", notes = "")
    @GetMapping("/getAll")
    public ResultView getAll1() {
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
    @GetMapping("/getById/{id}")
    public ResultView getById(@PathVariable @Range(min = 1L, max = 2L, message = "长度为 6-20 位") Long id) {
        UserModel model = userService.getById(id);
        return ResultView.success(model);
    }

    @ApiOperation(value = "新增", notes = "")
    @PostMapping("/add")
    public ResultView add(@RequestBody @Valid UserAddDTO dto) {
        UserModel model = new UserModel();
        BeanUtils.copyProperties(dto, model);
        userService.save(model);
        return ResultView.success(model);
    }

    @ApiOperation(value = "新增1", notes = "")
    @PostMapping("/add1")
    public ResultView add1(@RequestBody UserModel model) {
        UserAddDTO dto = new UserAddDTO();
        BeanUtils.copyProperties(model, dto);
        model = userService.add(dto);
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
    @DeleteMapping("/deleteById")
    @Valid
    public ResultView deleteById(@RequestParam @Range(min = 1L, max = 2L, message = "长度为 6-20 位") Long id) {
        return ResultView.success();
    }

    @ApiOperation(value = "根据id删除1", notes = "")
    @DeleteMapping("/deleteById1")
    @Validated
    public ResultView deleteById1(@RequestParam @Range(min = 1L, max = 2L, message = "长度为 6-20 位") Long id) {
        return ResultView.success();
    }

    @ApiOperation(value = "根据id删除2", notes = "")
    @DeleteMapping("/deleteById2")
    public ResultView deleteById2(@RequestParam @Validated @Range(min = 1L, max = 2L, message = "长度为 6-20 位") Long id) {
        return ResultView.success();
    }

    @ApiOperation(value = "根据id删除3", notes = "")
    @DeleteMapping("/deleteById3")
    public ResultView deleteById3(@RequestParam @Valid @Range(min = 1L, max = 2L, message = "长度为 6-20 位") Long id) {
        return ResultView.success();
    }

    @ApiOperation(value = "根据id删除4", notes = "")
    @DeleteMapping("/deleteById4")
    public ResultView deleteById4(@RequestParam Long id) {
        return ResultView.success();
    }

    @ApiOperation(value = "根据ids删除", notes = "")
    @DeleteMapping("/deleteByIds")
    public ResultView deleteByIds(@RequestParam Long[] ids) {
        userService.removeByIds(Arrays.asList(ids));
        return ResultView.success();
    }


}

