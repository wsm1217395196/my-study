package com.study.config;

import com.study.dto.ResourceRoleInfoDto;
import com.study.feign.UpmsFeign;
import com.study.model.ResourceRoleModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 动态获取url权限配置
 */
@Component
public class MySecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    private LinkedHashMap<String, Collection<ConfigAttribute>> map = null;
    private PathMatcher pathMatcher = new AntPathMatcher();//URLs匹配类

    @Autowired
    private MyConfig myConfig;
    @Autowired
    private UpmsFeign upmsFeign;

    /**
     * 加载权限表中所有操作请求权限
     */
    public void loadResourceDefine() {
        LinkedHashMap<String, Collection<ConfigAttribute>> linkedHashMap = new LinkedHashMap<>();
        //查当前项目的资源角色信息
        List<ResourceRoleInfoDto> dtos = upmsFeign.getResourceRoleInfo(myConfig.getProjectCode());
        for (ResourceRoleInfoDto dto : dtos) {
            String urlPattern = dto.getUrlPattern();//接口控制器名
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

                    SecurityConfig securityConfig = new SecurityConfig("" + model.getRoleId());
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
                System.err.println(key + " --- 路径下用户拥有这些角色id " + value.toString() + " 才可以访问！");
            } else {
                System.err.println(key + " --- 路径下所有用户不能访问！");
            }
        }
    }


    /**
     * 判定用户请求的url是否在权限表中
     * 如果在权限表中，则返回给MyAccessDecisionManager类decide方法，用来判定用户是否有此权限
     * 如果不在权限表中则放行
     *
     * @param o
     * @return
     * @throws IllegalArgumentException
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        //Object中包含用户请求request url
        String resUrl = ((FilterInvocation) o).getRequestUrl();

        List<String> urls = map.keySet().stream().filter(url -> pathMatcher.match(url, resUrl)).collect(Collectors.toList());
        if (urls.size() > 0 && map.get(urls.get(0)).size() > 0) {
            return map.get(urls.get(0));
        } else {
            return null;
        }

//        Iterator<String> iterator = map.keySet().iterator();
//        while (iterator.hasNext()) {
//            String url = iterator.next();
//            if (pathMatcher.match(url, resUrl)) {
//                return map.get(url);
//            }
//        }
//        return null;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
