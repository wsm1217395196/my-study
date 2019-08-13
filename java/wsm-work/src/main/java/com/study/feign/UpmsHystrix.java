package com.study.feign;

import com.study.dto.ResourceRoleInfoDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * wsm-upms服务的熔断器
 */
@Component
public class UpmsHystrix implements UpmsFeign {

    private final Logger logger = LoggerFactory.getLogger(UpmsHystrix.class);

    @Override
    public List<ResourceRoleInfoDto> getResourceRoleInfo(String projectCode) {
        System.err.println("调用wsm-upms服务getResourceRoleInfo方法失败!");
        logger.error("调用wsm-upms服务getResourceRoleInfo方法失败!");
        return null;
    }

    @Override
    public String getResourceIdsByClientId(String clientId) {
        System.err.println("调用wsm-upms服务getResourceIdsByClientId方法失败!");
        logger.error("调用wsm-upms服务getResourceIdsByClientId方法失败!");
        return null;
    }
}
