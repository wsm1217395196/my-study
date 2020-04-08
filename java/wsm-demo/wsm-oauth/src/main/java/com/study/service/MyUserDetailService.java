package com.study.service;

import com.study.dto.BaseDto;
import com.study.exception.MyRuntimeException;
import com.study.mapper.PublicMapper;
import com.study.model.UserModel;
import com.study.result.ResultEnum;
import com.study.result.ResultView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Security身份认证之UserDetailsService
 * <p>
 * 用户登录时（即携带参数请求/oauth/token接口）会调用这两个service。
 * 1、MyClientDetailsService是根据client_id查出来的信息验证用户登录时携带的参数（即客户端详情表信息）是否正确。并且设置能访问的资源id集合。
 * 2、MyUserDetailService是根据用户名去查用户密码，用户所拥有的角色等信息，然后丢给security去验证用户登录时的用户名和密码是否正确。
 * <p>
 * 以上都正确则返回token信息，并把token信息存到了token详情表，refresh_token详情表中（token存储方式为数据库）。然后就可以根据拿到的token去请求被security管理起来的接口地址。
 */
@Service
public class MyUserDetailService implements UserDetailsService {

    @Autowired
    private PublicMapper publicMapper;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        //查用户
        UserModel userModel = publicMapper.getUserByName(name);
        if (userModel == null) {
            throw new MyRuntimeException(ResultView.error(ResultEnum.CODE_5));
        }
        System.err.println("当前登录的用户是：" + name);

        //查该用户拥有的角色
        List<BaseDto> roleDtos = publicMapper.getRoleByUserId(userModel.getId());
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        if (roleDtos.size() == 0) {
            authorities.add(new SimpleGrantedAuthority("ROLE_NoRole"));
            System.err.println(name + "：用户没有角色！");
        } else {
            for (BaseDto roleDto : roleDtos) {
                authorities.add(new SimpleGrantedAuthority("ROLE_" + roleDto.getId()));
            }
        }

//        String password = {noop} + userModel.getPassword();
        String password = new BCryptPasswordEncoder().encode(userModel.getPassword());
        User user = new User(name, password, authorities);
        return user;
    }
}
