package com.study.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("my-config")
public class MyConfig {

    private boolean isUseSecurity;
    private String projectCode;
    private String clientId;
    private String rocketmq_namesrvAddr;
    private String rocketmq_Consumer;
    private String rocketmq_topic;

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

    public String getRocketmq_namesrvAddr() {
        return rocketmq_namesrvAddr;
    }

    public void setRocketmq_namesrvAddr(String rocketmq_namesrvAddr) {
        this.rocketmq_namesrvAddr = rocketmq_namesrvAddr;
    }

    public String getRocketmq_Consumer() {
        return rocketmq_Consumer;
    }

    public void setRocketmq_Consumer(String rocketmq_Consumer) {
        this.rocketmq_Consumer = rocketmq_Consumer;
    }

    public String getRocketmq_topic() {
        return rocketmq_topic;
    }

    public void setRocketmq_topic(String rocketmq_topic) {
        this.rocketmq_topic = rocketmq_topic;
    }
}
