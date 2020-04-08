package com.study.config;

import com.study.exception.MyRuntimeException;
import com.study.result.ResultEnum;
import com.study.result.ResultView;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.server.resource.BearerTokenAuthenticationToken;
import reactor.core.publisher.Mono;

/**
 * token校验
 */
public class MyReactiveAuthenticationManager implements ReactiveAuthenticationManager {

    private TokenStore tokenStore;

    public MyReactiveAuthenticationManager(TokenStore tokenStore) {
        this.tokenStore = tokenStore;
    }

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        return Mono.justOrEmpty(authentication)
                .filter(a -> a instanceof BearerTokenAuthenticationToken)
                .cast(BearerTokenAuthenticationToken.class)
                .map(BearerTokenAuthenticationToken::getToken)
                .flatMap((accessToken -> {
                    OAuth2AccessToken oAuth2AccessToken = this.tokenStore.readAccessToken(accessToken);
                    //根据access_token从数据库获取不到OAuth2AccessToken
                    if (oAuth2AccessToken == null) {
//                        return Mono.error(new InvalidTokenException("token无效！"));
                        return Mono.error(new MyRuntimeException(ResultView.error(ResultEnum.CODE_401)));
                    } else if (oAuth2AccessToken.isExpired()) {
//                        return Mono.error(new InvalidTokenException("token已过期！"));
                        return Mono.error(new MyRuntimeException(ResultView.error(ResultEnum.CODE_401)));
                    }

                    OAuth2Authentication oAuth2Authentication = this.tokenStore.readAuthentication(accessToken);
                    if (oAuth2Authentication == null) {
//                        return Mono.error(new InvalidTokenException("token无效！"));
                        return Mono.error(new MyRuntimeException(ResultView.error(ResultEnum.CODE_401)));
                    } else {
                        return Mono.just(oAuth2Authentication);
                    }
                })).cast(Authentication.class);
    }
}
