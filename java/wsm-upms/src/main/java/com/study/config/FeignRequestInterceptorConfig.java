package com.study.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 服务间调用feign传递header中的token 拦截器
 */
@Component
public class FeignRequestInterceptorConfig implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {
        System.err.println("调用feign传递header携带token");
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        if (request != null) {
//        只携带token
            String authorization = request.getHeader("Authorization");
            template.header("Authorization", authorization);

//        携带全部
//            Enumeration<String> headerNames = request.getHeaderNames();
//            if (headerNames != null) {
//                while (headerNames.hasMoreElements()) {
//                    String name = headerNames.nextElement();
//                    String values = request.getHeader(name);
//                    template.header(name, values);
//                }
//            }

        }
    }

}