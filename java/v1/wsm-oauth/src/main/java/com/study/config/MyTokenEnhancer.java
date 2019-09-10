package com.study.config;

import com.study.utils.DateUtil;
import org.json.JSONObject;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 自定义token生成携带的信息
 */
public class MyTokenEnhancer implements TokenEnhancer {

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        final Map<String, Object> additionalInfo = new HashMap<>();

        // 给/oauth/token接口加属性roles,author
        JSONObject jsonObject = new JSONObject(authentication.getPrincipal());
        List<Object> authorities = jsonObject.getJSONArray("authorities").toList();
        String roles = "";
        for (Object authority : authorities) {
            Map map = (Map) authority;
            roles += map.get("authority") + ",";
        }
        additionalInfo.put("roles", roles.substring(0, roles.length() - 1));
        additionalInfo.put("author", "wsm");
        additionalInfo.put("createTime", DateUtil.formatDate(new Date()));

        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
        return accessToken;
    }

}
