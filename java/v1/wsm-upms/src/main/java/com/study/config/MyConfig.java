package com.study.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "my-config")
public class MyConfig {

    private boolean isUseSecurity;
    private String projectCode;
    private String clientId;

    public boolean getIsUseSecurity() {
        return isUseSecurity;
    }

    public void setIsUseSecurity(boolean isUseSecurity) {
        this.isUseSecurity = isUseSecurity;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
}
