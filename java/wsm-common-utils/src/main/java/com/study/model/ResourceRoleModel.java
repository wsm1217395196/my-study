package com.study.model;

import java.io.Serializable;

/**
 * <p>
 * 资源-角色-关系表
 * </p>
 *
 * @author wsm
 * @since 2019-07-24
 */
public class ResourceRoleModel implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 资源id
     */
    private Long resourceId;
    /**
     * 资源按钮（页面要权限控制的按钮,也是后台接口方法名,多个用逗号分隔）
     */
    private String resourceButton;
    /**
     * 用户组id
     */
    private Long roleId;


    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

    public String getResourceButton() {
        return resourceButton;
    }

    public void setResourceButton(String resourceButton) {
        this.resourceButton = resourceButton;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    @Override
    public String toString() {
        return "ResourceRoleModel{" +
                ", resourceId=" + resourceId +
                ", resourceButton=" + resourceButton +
                ", roleId=" + roleId +
                "}";
    }
}
