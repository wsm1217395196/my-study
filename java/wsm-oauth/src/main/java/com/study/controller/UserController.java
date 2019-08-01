package com.study.controller;

import com.study.result.ResultEnum;
import com.study.result.ResultView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.security.Principal;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private ConsumerTokenServices consumerTokenServices;

    /**
     * 根据token获取登录用户主体相关信息（用户名，token，角色。。。等）
     *
     * @return
     */
    @GetMapping("/principal")
    public Principal user(@RequestParam(required = false) String clientName) {
        System.err.println(clientName + ":调用了principal");
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        return authentication;
    }

    /**
     * 清除token（注销登录）
     *
     * @param token
     * @return
     */
    @GetMapping("/logout")
    public ResultView logout(@RequestParam String token) {
        boolean b = consumerTokenServices.revokeToken(token);
        if (b) {
            return ResultView.success();
        } else {
            return ResultView.error(ResultEnum.CODE_8);
        }
    }

    /**
     * 解析jwt token
     *
     * @param token
     * @return
     */
    @GetMapping("/decodeToken")
    public ResultView decodeToken(@RequestParam String token) throws UnsupportedEncodingException {

//        Claims claims = Jwts.parser().setSigningKey("jwt_wsm".getBytes("UTF-8"))
//                .parseClaimsJws(token).getBody();
//        System.err.println(claims);
//
//        Object createTime = claims.get("createTime");
//        System.err.println("创建时间：" + createTime);
//
//        Date expiration = claims.getExpiration();
//        System.err.println("过期时间：" + expiration);
//
//        String author = (String) claims.get("author");
//        System.err.println("作者：" + author);

        Jwt decode = JwtHelper.decode(token);
        return ResultView.success(decode);
    }
}
