package com.study.feign;

import com.study.MyConstant;
import com.study.result.ResultView;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * feign调用wsm-oauth服务
 */
@FeignClient(value = MyConstant.wsm_oauth, fallback = OauthHystrix.class)
public interface OauthFeign {

    /**
     * 获取token
     *
     * @param username
     * @param password
     * @param client_id
     * @param client_secret
     * @param grant_type
     * @param scope
     * @return
     */
    @PostMapping("/oauth/token")
    Map<String, String> getOauthToken(@RequestParam("username") String username, @RequestParam("password") String password, @RequestParam("client_id") String client_id, @RequestParam("client_secret") String client_secret, @RequestParam("grant_type") String grant_type, @RequestParam("scope") String scope);

    /**
     * 清除token（注销登录）
     *
     * @return
     */
    @GetMapping("/user/logout")
    ResultView logout(@RequestParam("token") String token);

}
