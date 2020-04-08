package com.study.service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.study.mapper.UserRoleMapper;
import com.study.model.UserRoleModel;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户-角色-关系表 服务实现类
 * </p>
 *
 * @author wsm
 * @since 2019-07-18
 */
@Service
public class UserRoleService extends ServiceImpl<UserRoleMapper, UserRoleModel> {

}
