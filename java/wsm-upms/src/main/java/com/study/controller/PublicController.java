package com.study.controller;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.study.dto.BaseDto;
import com.study.mapper.RoleMapper;
import com.study.model.ResourceRoleModel;
import com.study.model.RoleModel;
import com.study.model.UserModel;
import com.study.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 公共控制器
 */
@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleMapper roleMapper;

    @GetMapping("/getUserByName")
    public UserModel getByName(@RequestParam String name) {
        EntityWrapper ew = new EntityWrapper();
        ew.eq("name", name);
        UserModel model = userService.selectOne(ew);
        return model;
    }

    @GetMapping("getRoleByUserId")
    public BaseDto getRoleByUserId(@RequestParam Long userId) {
        BaseDto dto = roleMapper.getRoleByUserId(userId);
        return dto;
    }
}
