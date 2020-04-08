package com.study.config;

import com.study.exception.MyAccessDeniedHandler;
import com.study.exception.MyAuthExceptionEntryPoint;
import com.study.mapper.PublicMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

/**
 * TODO  资源访问权限配置
 * 给接口地址让security管理起来，如哪些不需要授权能访问，
 * 哪些需要登录授权后能访问，哪些需要用户拥有这些角色才能访问。
 **/
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Value("${my-config.clientId}")
    private String clientId;
    @Autowired
    private PublicMapper publicMapper;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/test/**").authenticated()
                .antMatchers("/user/**").permitAll()
                .anyRequest().denyAll();
    }

    /**
     * 根据client_id设置资源id
     * 设置自定义授权异常信息
     */
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        String resourceIds = publicMapper.getResourceIdsByClientId(clientId);
        //设置客户端所能访问的资源id集合(默认取第一个是本服务的资源)
        resources.resourceId(resourceIds.split(",")[0]).stateless(true);
        //自定义Token异常信息,用于token校验失败返回信息
        resources.authenticationEntryPoint(new MyAuthExceptionEntryPoint())
                //授权异常处理
                .accessDeniedHandler(new MyAccessDeniedHandler());
    }

}

