package com.study.feign;

import com.study.MyConstant;
import com.study.dto.ResourceRoleInfoDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * feign调用wsm-upms服务
 */
@FeignClient(value = MyConstant.wsm_upms, fallback = UpmsHystrix.class)
public interface UpmsFeign {

    /**
     * 查当前项目的资源角色信息
     *
     * @param projectCode
     * @return
     */
    @GetMapping("/public/getResourceRoleInfo")
    List<ResourceRoleInfoDto> getResourceRoleInfo(@RequestParam("projectCode") String projectCode);

    /**
     * 根据client_id查询客户端resource_ids
     *
     * @param clientId
     * @return
     */
    @GetMapping("/public/getResourceIdsByClientId")
    String getResourceIdsByClientId(@RequestParam("clientId") String clientId);
}
