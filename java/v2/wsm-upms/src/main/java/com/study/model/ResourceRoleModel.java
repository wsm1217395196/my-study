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
 * 资源-角色-关系表
 * </p>
 *
 * @author wsm
 * @since 2019-10-24
 */
@Getter
@Setter
@ToString
@TableName("resource_role")
@ApiModel(value="ResourceRoleModel对象", description="资源-角色-关系表")
public class ResourceRoleModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "资源id")
    private Long resourceId;

    @ApiModelProperty(value = "资源按钮（页面要权限控制的按钮,也是后台接口方法名,多个用逗号分隔）")
    private String resourceButton;

    @ApiModelProperty(value = "角色id")
    private Long roleId;


}
