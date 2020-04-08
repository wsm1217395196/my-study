package com.study.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.study.MyConstant;
import com.study.feign.WorkFeign;
import com.study.mapper.RegionMapper;
import com.study.model.JobModel;
import com.study.model.RegionModel;
import com.study.result.PageParam;
import com.study.result.PageResult;
import com.study.result.ResultView;
import com.study.utils.CreateUtil;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>
 * 域（如一个公司） 服务实现类
 * </p>
 *
 * @author wsm
 * @since 2019-07-16
 */
@Service
public class RegionService extends ServiceImpl<RegionMapper, RegionModel> {

    @Autowired
    private RegionMapper regionMapper;
    @Autowired
    private WorkFeign workFeign;

    public PageResult getPage(PageParam pageParam) {
        int pageIndex = pageParam.getPageIndex();
        int pageSize = pageParam.getPageSize();
//        int pageStart = pageParam.getPageStart();
        String sort = pageParam.getSort();
        JSONObject object = new JSONObject(pageParam.getCondition());
        String name = object.getString("name").trim();
        String code = object.getString("code");
        String parentId = object.getString("parentId");
        String isEnable = object.getString("isEnable");

        EntityWrapper ew = new EntityWrapper();
        if (!StringUtils.isEmpty(name)) {
            ew.like("name", name);
        }
        if (!StringUtils.isEmpty(code)) {
            ew.eq("code", code);
        }
        if (!StringUtils.isEmpty(parentId)) {
            ew.eq("parent_id", parentId);
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
            total = regionMapper.selectCount(ew);
        }
        List records = regionMapper.selectPage(page, ew);
        PageResult pageResult = new PageResult(pageIndex, pageSize, total, records);
        return pageResult;
    }

    /**
     * 测试rocketmq分布式事务
     */
    public boolean distributedTransaction(String regionName) {
        RegionModel regionModel = new RegionModel();
        regionModel.setId(CreateUtil.id());
        regionModel.setName(regionName);
        boolean b = insert(regionModel);
        return b;
    }
}
