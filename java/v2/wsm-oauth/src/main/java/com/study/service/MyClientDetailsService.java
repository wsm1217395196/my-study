package com.study.service;

import com.study.exception.MyRuntimeException;
import com.study.mapper.PublicMapper;
import com.study.model.OauthClientDetailsModel;
import com.study.result.ResultEnum;
import com.study.result.ResultView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * 客户端详情信息，客户端详情信息在这里进行初始化，通过数据库来存储调取详情信息
 * <p>
 * 用户登录时（即携带参数请求/oauth/token接口）会调用这两个service。
 * 1、MyClientDetailsService是根据client_id查出来的信息验证用户登录时携带的参数（即客户端详情表信息）是否正确。并且设置能访问的资源id集合。
 * 2、MyUserDetailService是根据用户名去查用户密码，用户所拥有的角色等信息，然后丢给security去验证用户登录时的用户名和密码是否正确。
 * <p>
 * 以上都正确则返回token信息，并把token信息存到了token详情表，refresh_token详情表中（token存储方式为数据库）。然后就可以根据拿到的token去请求被security管理起来的接口地址。
 */
@Service
public class MyClientDetailsService implements ClientDetailsService {

    @Autowired
    private PublicMapper publicMapper;

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        OauthClientDetailsModel model = publicMapper.getOauthClientDetailsByClientId(clientId);
        if (model == null) {
            throw new MyRuntimeException(ResultView.error(ResultEnum.CODE_7));
        }
        BaseClientDetails clientDetails = new BaseClientDetails();
        clientDetails.setClientId(model.getClientId()); //客户端(client)id
        clientDetails.setResourceIds(Arrays.asList(model.getResourceIds().split(",")));//客户端所能访问的资源id集合
        clientDetails.setClientSecret(new BCryptPasswordEncoder().encode(model.getClientSecret()));//客户端(client)的访问密匙
        clientDetails.setAuthorizedGrantTypes(Arrays.asList(model.getAuthorizedGrantTypes().split(",")));//客户端支持的grant_type授权类型
        clientDetails.setScope(Arrays.asList(model.getScope().split(",")));//客户端申请的权限范围
        Integer accessTokenValidity = model.getAccessTokenValidity();
        if (accessTokenValidity != null && accessTokenValidity > 0) {
            clientDetails.setAccessTokenValiditySeconds(accessTokenValidity);//设置token的有效期，不设置默认12小时
        }
        Integer refreshTokenValidity = model.getRefreshTokenValidity();
        if (refreshTokenValidity != null && refreshTokenValidity > 0) {
            clientDetails.setRefreshTokenValiditySeconds(refreshTokenValidity);//设置刷新token的有效期，不设置默认30天
        }
        System.err.println("clientId是：" + clientId);
        return clientDetails;
    }
}
