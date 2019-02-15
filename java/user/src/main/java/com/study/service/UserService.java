package com.study.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.study.currency.PageParam;
import com.study.currency.PageResult;
import com.study.model.UserModel;
import com.study.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
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

    public PageResult getPage(PageParam pageParam) throws JSONException {
        String sort = pageParam.getSort();
        JSONObject object = new JSONObject(pageParam.getCondition());
        String name = object.getString("name");
        String nikename = object.getString("nikename");
        String sex = object.getString("sex");
        String isEnable = object.getString("isEnable");

        EntityWrapper ew = new EntityWrapper();
        if (!StringUtils.isEmpty(name)) {
            ew.eq("name", name);
        }
        if (!StringUtils.isEmpty(nikename)) {
            ew.eq("nikename", nikename);
        }
        if (!StringUtils.isEmpty(sex)) {
            ew.eq("sex", sex);
        }
        if (!StringUtils.isEmpty(isEnable)) {
            ew.eq("isEnable", isEnable);
        }
        if (!StringUtils.isEmpty(sort)) {
            ew.orderBy(sort);
        }

        Page page = new Page(pageParam.getPageStart(), pageParam.getPageSize());
        Integer total = userMapper.selectCount(ew);
        List list = userMapper.selectPage(page, ew);
        PageResult pageResult = new PageResult(pageParam.getPageIndex(), pageParam.getPageSize(), total, list);
        return pageResult;
    }
}
