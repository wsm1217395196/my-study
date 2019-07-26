package com.study.controller;

import com.study.result.ResultEnum;
import com.study.result.ResultView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public Principal user() {
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

}
