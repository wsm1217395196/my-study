package com.study.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.study.dto.ResourceRoleInfoDto;
import com.study.model.ResourceRoleModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 资源-角色-关系表 Mapper 接口
 * </p>
 *
 * @author wsm
 * @since 2019-07-18
 */
public interface ResourceRoleMapper extends BaseMapper<ResourceRoleModel> {

    /**
     * 查当前项目的资源角色信息
     *
     * @param projectCode
     * @return
     */
    List<ResourceRoleInfoDto> getResourceRoleInfo(@Param("projectCode") String projectCode);

}
