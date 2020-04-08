package com.study.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.MyConstant;
import com.study.mapper.JobMapper;
import com.study.model.JobModel;
import com.study.result.PageParam;
import com.study.result.PageResult;
import com.study.utils.MybatisPlusUtil;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 职位表 服务实现类
 * </p>
 *
 * @author wsm123
 * @since 2019-02-28
 */
@Service
public class JobService extends ServiceImpl<JobMapper, JobModel> implements IService<JobModel> {

    @Autowired
    private JobMapper jobMapper;

    public PageResult getPage(PageParam pageParam) {
        int pageIndex = pageParam.getPageIndex();
        int pageSize = pageParam.getPageSize();
        String sort = pageParam.getSort();
        JSONObject object = new JSONObject(pageParam.getCondition());

        QueryWrapper qw = new QueryWrapper();
        MybatisPlusUtil.eqUtil(qw, object, new String[]{"isEnabled"});
        MybatisPlusUtil.likeUtil(qw, object, new String[]{"name"});
        MybatisPlusUtil.sortUtil(qw, sort);

        Page page = new Page();
        int total = 0;
        if (pageIndex != MyConstant.Zero && pageSize != MyConstant.Zero) {
            page = new Page(pageIndex, pageSize);
            total = jobMapper.selectCount(qw);
        }
        List records = jobMapper.selectPage(page, qw).getRecords();
        PageResult pageResult = new PageResult(pageIndex, pageSize, total, records);
        return pageResult;
    }

    public List<JobModel> test() {
        Date startTime = new Date();
        Date endTime = new Date();
        List<JobModel> models = jobMapper.test(startTime, endTime);
        return models;
    }
}
