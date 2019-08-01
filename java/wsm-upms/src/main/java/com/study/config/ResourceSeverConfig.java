package com.study.config;

import com.study.dto.ResourceRoleInfoDto;
import com.study.mapper.ResourceRoleMapper;
import com.study.model.ResourceRoleModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
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

    @Value("${myConfig.isUseSecurity}")
    private boolean isUseSecurity;
    @Value("${myConfig.projectCode}")
    private String projectCode;
    @Autowired
    private ResourceRoleMapper resourceRoleMapper;
    @Autowired
    private RedisConnectionFactory redisConnectionFactory;
    @Autowired
    private DataSource dataSource;

    /**
     * 这个bean要与wsm-oauth服务AuthorizationServerConfig类tokenStore方法相对应
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

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf();//防csrf攻击
//        http.csrf().disable();//防csrf攻击 禁用

        if (!isUseSecurity) {//不启用权限
            http.authorizeRequests().antMatchers("/**").permitAll();//可以访问
        } else {//启用权限
            List<ResourceRoleInfoDto> dtos = resourceRoleMapper.getResourceRoleInfo(projectCode);//查当前项目的资源角色信息
            for (ResourceRoleInfoDto dto : dtos) {
                String resourceButton = dto.getButton();//资源里的button
                //urlPattern1、urlPattern2代表在这路径下的才被security权限管理起来
                String urlPattern1 = dto.getUrlPattern() + "/authority/**";
                String urlPattern2 = dto.getUrlPattern() + "/authority_button/**";

                //判断资源url有无角色信息
                if (dto.getResourceRoleModels().size() == 0) {
                    http.authorizeRequests().antMatchers(urlPattern1, urlPattern2).denyAll();
                    System.err.println(urlPattern1 + "、" + urlPattern2 + " 路径下所有用户不能访问！");
                } else {
                    String roleIds = ""; //urlPattern1的角色id

                    List<String> apiUrls = new ArrayList<>();//资源button里对应的api接口名
                    List<String> apiUrlRoleIds = new ArrayList<>();//资源button里对应的api接口名 ——对应的角色id
                    for (String button : resourceButton.split(",")) {
                        String apiUrl = dto.getUrlPattern() + "/authority_button/" + button;
                        apiUrls.add(apiUrl);
                        apiUrlRoleIds.add("");
                    }
                    for (ResourceRoleModel model : dto.getResourceRoleModels()) {
                        roleIds += "," + model.getRoleId();
                        //判断有无资源角色关系里的Button
                        String resourceRoleButton = model.getResourceButton();
                        if (!StringUtils.isEmpty(model.getResourceButton())) {
                            for (String roleButton : resourceRoleButton.split(",")) {
                                //在apiUrls集合中找到了资源角色里的button与之对应的api接口名，就添加角色id到apiUrlRoleIds集合中
                                int index = apiUrls.indexOf(dto.getUrlPattern() + "/authority_button/" + roleButton);
                                if (index != -1) {
                                    String apiUrlRoleIds_ = apiUrlRoleIds.get(index) + "," + model.getRoleId();
                                    apiUrlRoleIds.set(index, apiUrlRoleIds_);
                                }
                            }
                        }
                    }

                    //下面是 给某路径下 某角色赋予权限
                    roleIds = roleIds.substring(1, roleIds.length());
                    http.authorizeRequests().antMatchers(urlPattern1).hasAnyRole(roleIds.split(","));
                    System.err.println(urlPattern1 + " 路径下用户拥有这些角色id [" + roleIds + "] 才可以访问！");

                    for (int i = 0; i < apiUrls.size(); i++) {
                        String apiUrlRoleIdStr = apiUrlRoleIds.get(i);
                        if (!StringUtils.isEmpty(apiUrlRoleIdStr)) {
                            apiUrlRoleIdStr = apiUrlRoleIdStr.substring(1, apiUrlRoleIdStr.length());
                            String[] apiUrlRoleIds_ = apiUrlRoleIdStr.split(",");
                            http.authorizeRequests().antMatchers(apiUrls.get(i)).hasAnyRole(apiUrlRoleIds_);
                            System.err.println(apiUrls.get(i) + " 路径下用户拥有这些角色id " + Arrays.toString(apiUrlRoleIds_) + " 才可以访问！");
                        } else {
                            //这里看他是拥有 denyAll角色 能访问，实则不能访问（上面54行）（注：数据库的roleId不能是denyAll，要做限制）
                            http.authorizeRequests().antMatchers(apiUrls.get(i)).hasRole("denyAll");
                            System.err.println(apiUrls.get(i) + " 路径下所有用户不能访问");
                        }
                    }
                }
            }
        }
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
//        resources.resourceId("resourcesId").stateless(true);
        resources.tokenStore(tokenStore());
    }
}
