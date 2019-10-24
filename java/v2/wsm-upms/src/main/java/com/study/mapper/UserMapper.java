package com.study.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.study.model.UserModel;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户 Mapper 接口
 * </p>
 *
 * @author wsm
 * @since 2019-01-28
 */
@Mapper
public interface UserMapper extends BaseMapper<UserModel> {

}
