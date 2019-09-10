package com.study.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.study.dto.BaseDto;
import com.study.model.RoleModel;

/**
 * <p>
 * 角色 Mapper 接口
 * </p>
 *
 * @author wsm
 * @since 2019-07-18
 */
public interface RoleMapper extends BaseMapper<RoleModel> {

    BaseDto getRoleByUserId(Long userId);
}
