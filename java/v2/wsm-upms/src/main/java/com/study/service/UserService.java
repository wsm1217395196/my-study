package com.study.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.MyConstant;
import com.study.mapper.UserMapper;
import com.study.model.UserModel;
import com.study.result.PageParam;
import com.study.result.PageResult;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;


/**
 * <p>
 * 用户 服务实现类
 * </p>
 *
 * @author wsm
 * @since 2019-01-28
 */
@Service
public class UserService extends ServiceImpl<UserMapper, UserModel> {

    @Autowired
    private UserMapper userMapper;

    public PageResult getPage(PageParam pageParam) {
        int pageIndex = pageParam.getPageIndex();
        int pageSize = pageParam.getPageSize();
//        int pageStart = pageParam.getPageStart();
        String sort = pageParam.getSort();
        JSONObject object = new JSONObject(pageParam.getCondition());
        String name = object.getString("name").trim();
        String nickname = object.getString("nickname").trim();
        String phone = object.getString("phone").trim();
        String sex = object.getString("sex");
        String isEnable = object.getString("isEnable");

        QueryWrapper qw = new QueryWrapper();
        if (!StringUtils.isEmpty(name)) {
            qw.like("name", name);
        }
        if (!StringUtils.isEmpty(nickname)) {
            qw.like("nickname", nickname);
        }
        if (!StringUtils.isEmpty(phone)) {
            qw.like("phone", phone);
        }
        if (!StringUtils.isEmpty(sex)) {
            qw.eq("sex", sex);
        }
        if (!StringUtils.isEmpty(isEnable)) {
            qw.eq("isEnable", isEnable);
        }
//        if (!StringUtils.isEmpty(sort)) {
//            qw.orderBy(sort);
//        }

        Page page = new Page();
        int total = 0;
        if (pageIndex != MyConstant.Zero && pageSize != MyConstant.Zero) {
            page = new Page(pageIndex, pageSize);
            total = userMapper.selectCount(qw);
        }
        List records = userMapper.selectPage(page, qw).getRecords();
        PageResult pageResult = new PageResult(pageIndex, pageSize, total, records);
        return pageResult;
    }
}
