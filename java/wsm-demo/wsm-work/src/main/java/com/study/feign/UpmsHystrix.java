package com.study.feign;

import com.study.dto.ResourceRoleInfoDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * wsm-upms服务的熔断器
 */
@Slf4j
@Component
public class UpmsHystrix implements UpmsFeign {

    @Override
    public List<ResourceRoleInfoDto> getResourceRoleInfo(String projectCode) {
        System.err.println("调用wsm-upms服务getResourceRoleInfo方法失败");
        log.error("调用wsm-upms服务getResourceRoleInfo方法失败");
        return null;
    }

    @Override
    public String getResourceIdsByClientId(String clientId) {
        System.err.println("调用wsm-upms服务getResourceIdsByClientId方法失败");
        log.error("调用wsm-upms服务getResourceIdsByClientId方法失败");
        return null;
    }
}
