package com.study.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 用户组-资源-关系表
 * </p>
 *
 * @author wsm
 * @since 2019-07-16
 */
@TableName("user_group_resource_relation")
public class UserGroupResourceRelation implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户组id
     */
    @TableField("user_group_id")
    private Long userGroupId;
    /**
     * 资源id
     */
    @TableField("resource_id")
    private Long resourceId;
    /**
     * 资源按钮（页面要权限控制的按钮,也是后台接口方法名,多个用逗号分隔）
     */
    @TableField("resource_button")
    private String resourceButton;


    public Long getUserGroupId() {
        return userGroupId;
    }

    public void setUserGroupId(Long userGroupId) {
        this.userGroupId = userGroupId;
    }

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

    @Override
    public String toString() {
        return "UserGroupResourceRelation{" +
        ", userGroupId=" + userGroupId +
        ", resourceId=" + resourceId +
        ", resourceButton=" + resourceButton +
        "}";
    }
}
