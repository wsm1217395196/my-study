package com.study.config;

import com.study.dto.ResourceRoleInfoDto;
import com.study.exception.MyAccessDeniedHandler;
import com.study.exception.MyAuthExceptionEntryPoint;
import com.study.mapper.ResourceRoleMapper;
import com.study.model.ResourceRoleModel;
import com.study.service.OauthClientDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 配置资源服务器适配器
 */
@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ResourceSeverConfig extends ResourceServerConfigurerAdapter {

    @Autowired
    private MyConfig myConfig;
    @Autowired
    private ResourceRoleMapper resourceRoleMapper;
    @Autowired
    private OauthClientDetailsService oauthClientDetailsService;
    @Autowired
    private RedisConnectionFactory redisConnectionFactory;
    @Autowired
    private DataSource dataSource;
    @Autowired
    private MySecurityMetadataSource mySecurityMetadataSource;
    @Autowired
    private MyAccessDecisionManager myAccessDecisionManager;

    /**
     * 对Jwt签名时，增加一个密钥
     * JwtAccessTokenConverter：对Jwt来进行编码以及解码的类
     * 这个bean要与wsm-oauth服务AuthorizationServerConfig类JwtAccessTokenConverter方法相对应
     */
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {

        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();

        //对称加密方式
        jwtAccessTokenConverter.setSigningKey("jwt_wsm");

        //非对称加密方式(jks文件可能过期，jks文件需要Java keytool工具生成)
//        Resource resource = new ClassPathResource("publicKey.txt");
//        String publicKey ;
//        try {
//            publicKey = new String(FileCopyUtils.copyToByteArray(resource.getInputStream()));
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        jwtAccessTokenConverter.setVerifierKey(publicKey); //设置公钥

        return jwtAccessTokenConverter;
    }

    /**
     * application.yml有解释
     *
     * @return
     */
    @Bean
    public TokenStore tokenStore() {
//        return new InMemoryTokenStore();
        return new RedisTokenStore(redisConnectionFactory);
//        return new JwtTokenStore(jwtAccessTokenConverter());
//        return new JdbcTokenStore(dataSource);
    }

    /**
     * 根据client_id设置资源id
     * 设置自定义授权异常信息
     */
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        String resourceIds = oauthClientDetailsService.getResourceIdsByClientId(myConfig.getClientId());
        //设置客户端所能访问的资源id集合(默认取第一个是本服务的资源)
        resources.resourceId(resourceIds.split(",")[0]).stateless(true);
        resources.tokenStore(tokenStore());
        //自定义Token异常信息,用于token校验失败返回信息
        resources.authenticationEntryPoint(new MyAuthExceptionEntryPoint())
                //授权异常处理
                .accessDeniedHandler(new MyAccessDeniedHandler());
    }

    /**
     * 配置资源服务，根据项目code从数据库读取出来的角色，资源url信息（即接口地址）给接口使用security权限。
     * 如哪些不需要授权能访问，哪些需要登录授权后能访问，哪些需要用户拥有这些角色才能访问。
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();//防csrf攻击 禁用
        http.authorizeRequests().antMatchers("/public/**").permitAll();//可以访问

        //启用权限
        if (myConfig.getIsUseSecurity()) {
            http.authorizeRequests().antMatchers("/test/**").authenticated();

            //权限配置1（第一种实现：动态、修改了权限不需要重启项目，只需调用mySecurityMetadataSource类loadResourceDefine方法刷新权限，重新登陆获取token即可生效）
            http.authorizeRequests().withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                @Override
                public <O extends FilterSecurityInterceptor> O postProcess(O o) {
                    o.setSecurityMetadataSource(mySecurityMetadataSource); //动态获取url权限配置
                    o.setAccessDecisionManager(myAccessDecisionManager); //权限判断
                    return o;
                }
            });

            //权限配置2（第二种实现：修改了资源权限需要重启项目）
//            List<ResourceRoleInfoDto> dtos = resourceRoleMapper.getResourceRoleInfo(myConfig.getProjectCode());//查当前项目的资源角色信息
//            for (ResourceRoleInfoDto dto : dtos) {
//                String resourceButton = dto.getButton();//资源里的button
//                //urlPattern1、urlPattern2代表在这路径下的才被security权限管理起来
//                String urlPattern1 = dto.getUrlPattern() + "/authority/**";
//                String urlPattern2 = dto.getUrlPattern() + "/authority_button/**";
//
//                //判断资源url有无角色信息
//                if (dto.getResourceRoleModels().size() == 0) {
//                    http.authorizeRequests().antMatchers(urlPattern1, urlPattern2).denyAll();
//                    System.err.println(urlPattern1 + "、" + urlPattern2 + " --- 路径下所有用户不能访问！");
//                } else {
//                    String roleIds = ""; //urlPattern1的角色id
//
//                    List<String> apiUrls = new ArrayList<>();//资源button里对应的api接口名
//                    List<String> apiUrlRoleIds = new ArrayList<>();//资源button里对应的api接口名 ——对应的角色id
//                    for (String button : resourceButton.split(",")) {
//                        String apiUrl = dto.getUrlPattern() + "/authority_button/" + button;
//                        apiUrls.add(apiUrl);
//                        apiUrlRoleIds.add("");
//                    }
//                    for (ResourceRoleModel model : dto.getResourceRoleModels()) {
//                        roleIds += "," + model.getRoleId();
//                        //判断有无资源角色关系里的Button
//                        String resourceRoleButton = model.getResourceButton();
//                        if (!StringUtils.isEmpty(model.getResourceButton())) {
//                            for (String roleButton : resourceRoleButton.split(",")) {
//                                //在apiUrls集合中找到了资源角色里的button与之对应的api接口名，就添加角色id到apiUrlRoleIds集合中
//                                int index = apiUrls.indexOf(dto.getUrlPattern() + "/authority_button/" + roleButton);
//                                if (index != -1) {
//                                    String apiUrlRoleIds_ = apiUrlRoleIds.get(index) + "," + model.getRoleId();
//                                    apiUrlRoleIds.set(index, apiUrlRoleIds_);
//                                }
//                            }
//                        }
//                    }
//
//                    //下面是 给某路径下 某角色赋予权限
//                    roleIds = roleIds.substring(1, roleIds.length());
//                    http.authorizeRequests().antMatchers(urlPattern1).hasAnyRole(roleIds.split(","));
//                    System.err.println(urlPattern1 + " --- 路径下用户拥有这些角色id [" + roleIds + "] 才可以访问！");
//
//                    for (int i = 0; i < apiUrls.size(); i++) {
//                        String apiUrlRoleIdStr = apiUrlRoleIds.get(i);
//                        if (!StringUtils.isEmpty(apiUrlRoleIdStr)) {
//                            apiUrlRoleIdStr = apiUrlRoleIdStr.substring(1, apiUrlRoleIdStr.length());
//                            String[] apiUrlRoleIds_ = apiUrlRoleIdStr.split(",");
//                            http.authorizeRequests().antMatchers(apiUrls.get(i)).hasAnyRole(apiUrlRoleIds_);
//                            System.err.println(apiUrls.get(i) + " --- 路径下用户拥有这些角色id " + Arrays.toString(apiUrlRoleIds_) + " 才可以访问！");
//                        } else {
//                            http.authorizeRequests().antMatchers(apiUrls.get(i)).denyAll();
//                            System.err.println(apiUrls.get(i) + " --- 路径下所有用户不能访问");
//                        }
//                    }
//                }
//            }

        }
    }
}
