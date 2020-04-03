package com.study.config;

import com.study.dto.ResourceRoleInfoDto;
import com.study.mapper.ResourceRoleMapper;
import com.study.model.ResourceRoleModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 权限判断
 */
@Component
public class MyReactiveAuthorizationManager implements ReactiveAuthorizationManager<AuthorizationContext> {

    private LinkedHashMap<String, Collection<ConfigAttribute>> map = null;
    private Set<String> permitAll = new LinkedHashSet<>();
    private PathMatcher pathMatcher = new AntPathMatcher();//URLs匹配类

    @Autowired
    private MyConfig myConfig;
    @Autowired
    private ResourceRoleMapper resourceRoleMapper;

    public MyReactiveAuthorizationManager() {
        setPermitAll();//设置直接放行的地址
    }

    /**
     * 实现权限验证判断
     */
    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> authenticationMono, AuthorizationContext authorizationContext) {
        ServerWebExchange exchange = authorizationContext.getExchange();
        //请求资源
        String requestPath = exchange.getRequest().getURI().getPath();
        // 是否直接放行
        if (permitAll(requestPath)) {
            return Mono.just(new AuthorizationDecision(true));
        }
        //权限校验
        return authenticationMono.map(auth -> {
            return new AuthorizationDecision(checkAuthorities(auth, requestPath));
        }).defaultIfEmpty(new AuthorizationDecision(false));

    }

    /**
     * 校验是否属于静态资源
     *
     * @param requestPath 请求路径
     * @return
     */
    private boolean permitAll(String requestPath) {
        return permitAll.stream()
                .filter(r -> pathMatcher.match(r, requestPath)).findFirst().isPresent();
    }

    //权限校验
    private boolean checkAuthorities(Authentication auth, String requestPath) {
        List<String> urls = map.keySet().stream().filter(url -> pathMatcher.match(url, requestPath)).collect(Collectors.toList());
        if (urls.size() == 1) {
            Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
            Collection<ConfigAttribute> configAttributes = map.get(urls.get(0));

            Iterator<ConfigAttribute> iterator = configAttributes.iterator();
            while (iterator.hasNext()) {
                ConfigAttribute configAttribute = iterator.next();
                String attribute = configAttribute.getAttribute();
                for (GrantedAuthority grantedAuthority : authorities) {
                    // 匹配用户拥有的grantedAuthority权限 和 系统中的needPerm权限
                    String authority = grantedAuthority.getAuthority();
                    if (attribute.equals(authority)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 加载权限表中所有操作请求权限
     */
    public void loadResourceDefine() {
        LinkedHashMap<String, Collection<ConfigAttribute>> linkedHashMap = new LinkedHashMap<>();
        //查当前项目的资源角色信息
        List<ResourceRoleInfoDto> dtos = resourceRoleMapper.getResourceRoleInfo(myConfig.getRegionCode());
        for (ResourceRoleInfoDto dto : dtos) {
            String projectCode = dto.getProjectCode(); //项目编码（即网关前缀）
            String urlPattern = "/" + projectCode + dto.getUrlPattern();//接口控制器名
            String[] resourceButtons = dto.getButton().split(",");//资源里的button
            //urlPattern1、urlPattern2代表在这路径下的才被security权限管理起来
            String urlPattern1 = urlPattern + "/authority/**";
            String urlPattern2 = urlPattern + "/authority_button/**";

            //角色信息1（对应urlPattern1）
            Collection<ConfigAttribute> configAttributes1 = new ArrayList<>();

            //判断资源url里有无角色信息
            List<ResourceRoleModel> resourceRoleModels = dto.getResourceRoleModels();
            if (resourceRoleModels.size() == 0) {
                linkedHashMap.put(urlPattern1, configAttributes1);
                linkedHashMap.put(urlPattern2, configAttributes1);
                System.err.println(urlPattern1 + "、" + urlPattern2 + " 路径下所有用户不能访问！");
            } else {
                for (ResourceRoleModel model : resourceRoleModels) {

                    SecurityConfig securityConfig = new SecurityConfig("ROLE_" + model.getRoleId());
                    configAttributes1.add(securityConfig);

                    //查找资源里的button在资源角色里的button存在,则put角色集合（赋予某角色权限）,不存在则put角色集合。
                    String[] resourceRoleButtons = model.getResourceButton().split(",");
                    for (String resourceButton : resourceButtons) {
                        String urlPatternButton = urlPattern + "/authority_button/" + resourceButton;
                        long count = Arrays.stream(resourceRoleButtons).filter(x -> x.equals(resourceButton)).count();
                        if (count > 0) {
                            //角色信息2（对应urlPattern2）
                            Collection<ConfigAttribute> configAttributes2 = new ArrayList<>();
                            configAttributes2.add(securityConfig);
                            if (!linkedHashMap.containsKey(urlPatternButton)) {
                                linkedHashMap.put(urlPatternButton, configAttributes2);
                            } else {
                                linkedHashMap.get(urlPatternButton).add(securityConfig);
                            }
                        } else {
                            if (!linkedHashMap.containsKey(urlPatternButton)) {
                                linkedHashMap.put(urlPatternButton, new ArrayList<>());
                            }
                        }
                    }
                }
                //给urlPattern1路径下 赋予某角色权限
                linkedHashMap.put(urlPattern1, configAttributes1);
            }
        }
        map = linkedHashMap;

        //下面是打印
        Iterator<Map.Entry<String, Collection<ConfigAttribute>>> iterator = linkedHashMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Collection<ConfigAttribute>> next = iterator.next();
            String key = next.getKey();
            Collection<ConfigAttribute> value = next.getValue();
            if (value.size() > 0) {
                System.err.println(key + " --- 路径下用户拥有这些角色" + value.toString() + " 才可以访问！");
            } else {
                System.err.println(key + " --- 路径下所有用户不能访问！");
            }
        }
    }

    public void setPermitAll() {
        permitAll.add("/swagger-ui.html");
        permitAll.add("/wsm-oauth/**");
    }
}
