package com.study.dto;

import com.study.model.ResourceRoleModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * 资源角色信息
 *
 * @author wsm
 * @since 2019-07-24
 */
@Getter
@Setter
@ToString
@ApiModel(value = "ResourceRoleInfoDto对象", description = "资源角色信息")
public class ResourceRoleInfoDto {

    /**
     * 权限API路径匹配(注：即后台控制器名，后面带authority,或者authority_button(对应button字段)代表受权限控制)
     */
    @ApiModelProperty(value = "权限API路径匹配(注：即后台控制器名，后面带authority,或者authority_button(对应button字段)代表受权限控制)")
    private String urlPattern;
    /**
     * 按钮（页面要权限控制的按钮,也是后台接口方法名,多个用逗号分隔）
     */
    @ApiModelProperty(value = "按钮（页面要权限控制的按钮,也是后台接口方法名,多个用逗号分隔）")
    private String button;
    /**
     * 权限API路径匹配(注：即后台控制器名，后面带authority,或者authority_button(对应button字段)代表受权限控制)
     * 所对应的角色信息
     */
    @ApiModelProperty(value = "所对应的角色信息，权限API路径匹配(注：即后台控制器名，后面带authority,或者authority_button(对应button字段)代表受权限控制)")
    private List<ResourceRoleModel> resourceRoleModels;

}
