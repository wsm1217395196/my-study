package com.study.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.study.MyConstant;
import com.study.mapper.WorkMapper;
import com.study.model.WorkModel;
import com.study.result.PageParam;
import com.study.result.PageResult;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>
 * 职业信息表 服务实现类
 * </p>
 *
 * @author wsm123
 * @since 2019-02-28
 */
@Service
public class WorkService extends ServiceImpl<WorkMapper, WorkModel> implements IService<WorkModel> {

    @Autowired
    private WorkMapper workMapper;

    public PageResult getPage(PageParam pageParam) {
        int pageIndex = pageParam.getPageIndex();
        int pageSize = pageParam.getPageSize();
//        int pageStart = pageParam.getPageStart();
        String sort = pageParam.getSort();
        JSONObject object = new JSONObject(pageParam.getCondition());
        Long recruitPlatformId = object.getLong("recruitPlatformId");
        Long jobId = object.getLong("jobId");
        String site = object.getString("site").trim();
        String isEnable = object.getString("isEnable");

        EntityWrapper ew = new EntityWrapper();
        if (!StringUtils.isEmpty(recruitPlatformId)) {
            ew.eq("recruit_platform_id", recruitPlatformId);
        }
        if (!StringUtils.isEmpty(jobId)) {
            ew.eq("job_id", jobId);
        }
        if (!StringUtils.isEmpty(site)) {
            ew.like("site", site);
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
            total = workMapper.selectCount(ew);
        }
        List records = workMapper.selectPage(page, ew);
        PageResult pageResult = new PageResult(pageIndex, pageSize, total, records);
        return pageResult;
    }
}
