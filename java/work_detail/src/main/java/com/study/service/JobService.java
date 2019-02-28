package com.study.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.study.currency.PageParam;
import com.study.currency.PageResult;
import com.study.model.JobModel;
import com.study.mapper.JobMapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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

    public PageResult getPage(PageParam pageParam) throws JSONException {
        String sort = pageParam.getSort();
        JSONObject object = new JSONObject(pageParam.getCondition());
        String name = object.getString("name");
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

        Page page = new Page(pageParam.getPageStart(), pageParam.getPageSize());
        Integer total = jobMapper.selectCount(ew);
        List list = jobMapper.selectPage(page, ew);
        PageResult pageResult = new PageResult(pageParam.getPageIndex(), pageParam.getPageSize(), total, list);
        return pageResult;
    }
}
