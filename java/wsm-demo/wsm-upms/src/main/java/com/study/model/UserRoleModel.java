package com.study.model;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * <p>
 * 用户-角色-关系表
 * </p>
 *
 * @author wsm
 * @since 2019-10-24
 */
@Getter
@Setter
@ToString
@TableName("user_role")
@ApiModel(value="UserRoleModel对象", description="用户-角色-关系表")
public class UserRoleModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户id")
    private Long userId;

    @ApiModelProperty(value = "角色id")
    private Long roleId;


}
