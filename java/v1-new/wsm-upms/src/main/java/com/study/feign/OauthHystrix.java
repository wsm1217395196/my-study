package com.study.feign;

import com.study.MyConstant;
import com.study.result.ResultView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * wsm-oauth服务的熔断器
 */
@Component
public class OauthHystrix implements OauthFeign {

    private final Logger logger = LoggerFactory.getLogger(OauthHystrix.class);

    @Override
    public Map<String, String> getOauthToken(String username, String password, String client_id, String client_secret, String grant_type, String scope) {
        System.err.println("调用wsm-oauth服务getOauthToken方法失败!");
        logger.error("调用wsm-oauth服务getOauthToken方法失败!");
        return null;
    }

    @Override
    public ResultView logout(String token) {
        System.err.println("调用wsm-oauth服务logout方法失败!");
        logger.error("调用wsm-oauth服务logout方法失败!");
        return ResultView.hystrixError(MyConstant.wsm_oauth);
    }
}
