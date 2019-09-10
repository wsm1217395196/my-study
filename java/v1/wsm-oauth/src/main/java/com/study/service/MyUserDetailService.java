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
 */
@Service
public class MyUserDetailService implements UserDetailsService {

    @Autowired
    private PublicMapper publicMapper;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        UserModel userModel = publicMapper.getUserByName(name);
        if (userModel == null) {
            throw new MyRuntimeException(ResultView.error(ResultEnum.CODE_5));
        }
//        String password = {noop} + userModel.getPassword();
        String password = new BCryptPasswordEncoder().encode(userModel.getPassword());
        //查该用户拥有的角色
        List<BaseDto> roleDtos = publicMapper.getRoleByUserId(userModel.getId());
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (BaseDto roleDto : roleDtos) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + roleDto.getId()));
        }
        User user = new User(name, password, authorities);
        System.err.println("当前登录的用户是：" + name);
        return user;
    }
}
