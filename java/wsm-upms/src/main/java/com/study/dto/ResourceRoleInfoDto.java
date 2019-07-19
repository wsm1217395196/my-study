package com.study.dto;

import com.study.model.ResourceRoleModel;

import java.util.List;

/**
 * 资源角色信息
 */
public class ResourceRoleInfoDto {

    /**
     * 权限API路径匹配
     */
    private String urlPattern;
    /**
     * 权限API路径匹配 所对应的角色信息
     */
    private List<ResourceRoleModel> resourceRoleModels;

    public String getUrlPattern() {
        return urlPattern;
    }

    public void setUrlPattern(String urlPattern) {
        this.urlPattern = urlPattern;
    }

    public List<ResourceRoleModel> getResourceRoleModels() {
        return resourceRoleModels;
    }

    public void setResourceRoleModels(List<ResourceRoleModel> resourceRoleModels) {
        this.resourceRoleModels = resourceRoleModels;
    }
}
