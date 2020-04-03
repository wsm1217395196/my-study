package com.study.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "my-config")
public class MyConfig {

    private boolean isUseSecurity;
    private String regionCode;

    public boolean getIsUseSecurity() {
        return isUseSecurity;
    }

    public void setIsUseSecurity(boolean isUseSecurity) {
        this.isUseSecurity = isUseSecurity;
    }

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }
}
