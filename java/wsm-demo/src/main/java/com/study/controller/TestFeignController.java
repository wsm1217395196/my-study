package com.study.controller;


import com.study.currency.result.ResultView;
import com.study.feign.WorkFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 测试 前端控制器
 * </p>
 *
 * @author wsm
 * @since 2019-01-28
 */
@Api(description = "测试控制器")
@RestController
@RequestMapping("/testFeign")
public class TestFeignController {

    @Autowired
    private WorkFeign workInterface;

    @ApiOperation(value = "根据id查询work", notes = "")
    @GetMapping("/getById/{id}")
    public ResultView getById(@PathVariable Long id) {
        ResultView resultView = workInterface.getById(id);
        return resultView;
    }
}

