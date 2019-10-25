package com.study.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.MyConstant;
import com.study.mapper.UserMapper;
import com.study.model.UserModel;
import com.study.result.PageParam;
import com.study.result.PageResult;
import com.study.utils.MybatisPlusUtil;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        String sort = pageParam.getSort();
        JSONObject object = new JSONObject(pageParam.getCondition());

        QueryWrapper qw = new QueryWrapper();
        MybatisPlusUtil.eqUtil(qw, object, new String[]{"sex", "status"});
        MybatisPlusUtil.likeUtil(qw, object, new String[]{"name", "nickname", "phone"});
        MybatisPlusUtil.sortUtil(qw, sort);

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
