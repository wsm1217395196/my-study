package com.study.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * 服务间调用feign传递header中的token 拦截器
 */
@Component
public class FeignRequestInterceptorConfig implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        if (request != null) {
            System.err.println("调用feign传递header携带token");
//            System.out.println("lcn组：" + TracingContext.tracing().groupId());

//        只携带token
//            String authorization = request.getHeader("Authorization");
//            requestTemplate.header("Authorization", authorization);
//
//            String xid = RootContext.getXID();
//            if (StringUtils.isNotBlank(xid)) {
//                System.out.println("xid：" + xid);
//                requestTemplate.header("Xid_Header", xid);
//            }

            //lcn
//            Tracings.transmit((x$0, xva$1) -> {
//            requestTemplate.header(x$0, new String[]{xva$1});
//        });

//        携带全部
            Enumeration<String> headerNames = request.getHeaderNames();
            if (headerNames != null) {
                while (headerNames.hasMoreElements()) {
                    String name = headerNames.nextElement();
                    String values = request.getHeader(name);
                    requestTemplate.header(name, values);
                }
            }

        }


//        lcn测试
//        System.err.println("调用feign传递header携带token");
//        System.out.println("lcn组：" + TracingContext.tracing().groupId());
//        requestTemplate.header("Authorization", "bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsicmVzb3VyY2Vfb2F1dGgiLCJyZXNvdXJjZV91cG1zIiwicmVzb3VyY2Vfd29yayJdLCJjcmVhdGVUaW1lIjoiMjAxOS0wOC0yNyAxMDowNDo1MyIsInVzZXJfbmFtZSI6IndzbTEiLCJhdXRob3IiOiJ3c20iLCJzY29wZSI6WyJzY29wZV93c20iXSwicm9sZXMiOiJST0xFXzEiLCJleHAiOjE1NjY5MTQ2OTIsImF1dGhvcml0aWVzIjpbIlJPTEVfMSJdLCJqdGkiOiJjYzdiM2E4Zi05YWI4LTRmZDktYTY5Yy1kZjA3MTA3YWI2YjgiLCJjbGllbnRfaWQiOiJjbGllbnRfdXBtcyJ9.6T4nvbqPt4wtFgHqNzNkNkdSBOpKWPpDmRcMHf7rnTU");
//        Tracings.transmit((x$0, xva$1) -> {
//            requestTemplate.header(x$0, new String[]{xva$1});
//        });
    }

}