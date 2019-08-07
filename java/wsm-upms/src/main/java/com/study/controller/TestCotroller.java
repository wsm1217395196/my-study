package com.study.controller;

import com.study.feign.WorkFeign;
import com.study.model.JobModel;
import com.study.model.RegionModel;
import com.study.result.ResultView;
import com.study.service.RegionService;
import com.study.utils.CreateUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试控制器
 */
@RestController
@RequestMapping("/test")
public class TestCotroller {

    @Autowired
    private RegionService regionService;
    @Autowired
    private WorkFeign workFeign;

    /**
     * 测试分布式事务
     *
     * @return
     */
    @ApiOperation(value = "测试分布式事务")
    @GetMapping("/distributedTransaction")
    @Transactional
    public ResultView distributedTransaction() {
        RegionModel regionModel = new RegionModel();
        regionModel.setId(CreateUtil.id());
        regionModel.setName("测试分布式事务" + CreateUtil.validateCode(3));
        regionService.insert(regionModel);

        JobModel jobModel = new JobModel();
        jobModel.setId(CreateUtil.id());
        jobModel.setName("测试分布式事务" + CreateUtil.validateCode(3));
        ResultView resultView = workFeign.JobAdd(jobModel);
        System.err.println(resultView);

        return ResultView.success();
    }
}
