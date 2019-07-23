package com.study.config;

import com.study.dto.ResourceRoleInfoDto;
import com.study.mapper.ResourceRoleMapper;
import com.study.model.ResourceRoleModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

import java.util.ArrayList;
import java.util.List;

/**
 * 配置资源服务器适配器
 */
@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ResourceSeverConfig extends ResourceServerConfigurerAdapter {

    @Value("${myConfig.isStartSecurity}")
    private boolean isStartSecurity;

    @Value("${myConfig.projectCode}")
    private String projectCode;

    @Autowired
    private ResourceRoleMapper resourceRoleMapper;

    @Override
    public void configure(HttpSecurity http) throws Exception {

        if (!isStartSecurity) {//不启用权限
            http.csrf().disable().//防csrf攻击
                    authorizeRequests().antMatchers("/**").permitAll();//可以访问
        } else {//启用权限
            List<ResourceRoleInfoDto> dtos = resourceRoleMapper.getResourceRoleInfo(projectCode);//查当前项目的资源角色信息
            http.csrf().disable();

            for (ResourceRoleInfoDto dto : dtos) {
                //判断资源url有无角色信息
                if (dto.getResourceRoleModels().size() == 0) {
                    http.authorizeRequests().antMatchers(dto.getUrlPattern() + "/**").permitAll();
                    System.err.println(dto.getUrlPattern() + "/** 路径下所有用户不能访问！");
                } else {
                    List<Long> roleIds = new ArrayList<>();
                    for (ResourceRoleModel model : dto.getResourceRoleModels()) {
                        roleIds.add(model.getRoleId());
                        http.authorizeRequests().antMatchers(dto.getUrlPattern() + "/**").hasRole(Long.toString(model.getRoleId()));
                    }
                    System.err.println(dto.getUrlPattern() + "/** 路径下用户拥有这些角色id " + roleIds.toString() + " 才可以访问！");
                }
            }

        }

    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.resourceId("resourcesId").stateless(true);
    }
}
