package com.study.feign;

import com.study.MyConstant;
import com.study.result.ResultEnum;
import com.study.result.ResultView;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * wsm-oauth服务的熔断器
 */
@Component
public class OauthHystrix implements OauthFeign {

    @Override
    public Map<String, String> getOauthToken(String username, String password, String client_id, String client_secret, String grant_type, String scope) {
        Map map = new HashMap();
        map.put("code", ResultEnum.CODE_3.getCode());
        return map;
    }

    @Override
    public ResultView logout(String token) {
        return ResultView.hystrixError(MyConstant.wsm_oauth);
    }
}
