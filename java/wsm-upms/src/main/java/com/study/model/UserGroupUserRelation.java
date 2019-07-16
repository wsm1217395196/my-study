package com.study.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 用户组-用户-关系表
 * </p>
 *
 * @author wsm
 * @since 2019-07-16
 */
@TableName("user_group_user_relation")
public class UserGroupUserRelation implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户组id
     */
    @TableField("user_group_id")
    private Long userGroupId;
    /**
     * 用户id
     */
    @TableField("user_id")
    private Long userId;


    public Long getUserGroupId() {
        return userGroupId;
    }

    public void setUserGroupId(Long userGroupId) {
        this.userGroupId = userGroupId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "UserGroupUserRelation{" +
        ", userGroupId=" + userGroupId +
        ", userId=" + userId +
        "}";
    }
}
