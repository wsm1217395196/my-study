package com.study.service;

import com.study.mapper.PublicMapper;
import com.study.model.OauthClientDetailsModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class MyClientDetailsService implements ClientDetailsService {

    @Autowired
    private PublicMapper publicMapper;

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        OauthClientDetailsModel model = publicMapper.getOauthClientDetailsByClientId(clientId);

        BaseClientDetails clientDetails = new BaseClientDetails();
        clientDetails.setClientId(model.getClientId());
        clientDetails.setClientSecret(new BCryptPasswordEncoder().encode(model.getClientSecret()));

        clientDetails.setAuthorizedGrantTypes(Arrays.asList(model.getAuthorizedGrantTypes().split(",")));
        clientDetails.setScope(Arrays.asList(model.getScope().split(",")));

        Integer accessTokenValidity = model.getAccessTokenValidity();
        if (accessTokenValidity != null && accessTokenValidity > 0) {
            clientDetails.setAccessTokenValiditySeconds(accessTokenValidity);//不设置默认12小时
        }

        Integer refreshTokenValidity = model.getRefreshTokenValidity();
        if (refreshTokenValidity != null && refreshTokenValidity > 0) {
            clientDetails.setRefreshTokenValiditySeconds(refreshTokenValidity);//不设置默认30天
        }
        System.err.println("clientId是：" + clientId);
        return clientDetails;
    }
}
