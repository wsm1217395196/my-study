package com.study.feign;

import com.study.dto.ResourceRoleInfoDto;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * wsm-upms服务的熔断器
 */
@Component
public class UpmsHystrix implements UpmsFeign {

    @Override
    public List<ResourceRoleInfoDto> getResourceRoleInfo(String projectCode) {
        System.err.println("调用wsm-upms服务失败!");
        return null;
    }
}
