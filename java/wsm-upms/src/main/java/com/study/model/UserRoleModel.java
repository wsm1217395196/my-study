package com.study.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 用户-角色-关系表
 * </p>
 *
 * @author wsm
 * @since 2019-07-18
 */
@TableName("user_role")
public class UserRoleModel implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    @TableField("user_id")
    private Long userId;
    /**
     * 用户组id
     */
    @TableField("role_id")
    private Long roleId;


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    @Override
    public String toString() {
        return "UserRoleModel{" +
        ", userId=" + userId +
        ", roleId=" + roleId +
        "}";
    }
}
