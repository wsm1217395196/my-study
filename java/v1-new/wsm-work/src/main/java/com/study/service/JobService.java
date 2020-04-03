package com.study.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.study.MyConstant;
import com.study.mapper.JobMapper;
import com.study.model.JobModel;
import com.study.result.PageParam;
import com.study.result.PageResult;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
//        int pageStart = pageParam.getPageStart();
        String sort = pageParam.getSort();
        JSONObject object = new JSONObject(pageParam.getCondition());
        String name = object.getString("name").trim();
        String isEnable = object.getString("isEnable");

        EntityWrapper ew = new EntityWrapper();
        if (!StringUtils.isEmpty(name)) {
            ew.like("name", name);
        }
        if (!StringUtils.isEmpty(isEnable)) {
            ew.eq("isEnable", isEnable);
        }
        if (!StringUtils.isEmpty(sort)) {
            ew.orderBy(sort);
        }

        Page page = new Page();
        int total = 0;
        if (pageIndex != MyConstant.Zero && pageSize != MyConstant.Zero) {
            page = new Page(pageIndex, pageSize);
            total = jobMapper.selectCount(ew);
        }
        List records = jobMapper.selectPage(page, ew);
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
