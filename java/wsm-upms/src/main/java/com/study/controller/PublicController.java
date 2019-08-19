package com.study.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.study.MyConstant;
import com.study.config.MyConfig;
import com.study.dto.ResourceRoleInfoDto;
import com.study.exception.MyRuntimeException;
import com.study.feign.OauthFeign;
import com.study.mapper.ResourceRoleMapper;
import com.study.model.OauthClientDetailsModel;
import com.study.model.UserModel;
import com.study.result.ResultEnum;
import com.study.result.ResultView;
import com.study.service.OauthClientDetailsService;
import com.study.service.UserService;
import com.study.utils.CreateUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 公共控制器
 */
@Api(description = "公共控制器")
@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private MyConfig myConfig;
    @Autowired
    private UserService userService;
    @Autowired
    private OauthClientDetailsService oauthClientDetailsService;
    @Autowired
    private OauthFeign oauthFeign;
    @Autowired
    private ResourceRoleMapper resourceRoleMapper;

    @ApiOperation(value = "注册", notes = "提交参数：{\"name\":\"wsm666\",\"password\":\"123456\"}")
    @PostMapping("/register")
    public ResultView register(@RequestBody String body) {
        JSONObject jsonObject = new JSONObject(body);
        String name = jsonObject.getString("name");
        String password = jsonObject.getString("password");

        //查账号是否存在
        EntityWrapper ew = new EntityWrapper();
        ew.eq("name", name);
        int count = userService.selectCount(ew);
        if (count > 0) {
            return ResultView.error(ResultEnum.CODE_6);
        }

        //注册
        UserModel model = new UserModel();
        model.setId(CreateUtil.id());
        model.setName(name);
        model.setPassword(password);
        model.setCreateTime(new Date());
        userService.insert(model);
        return ResultView.success();
    }

    @ApiOperation(value = "登录", notes = "提交参数：{\"name\":\"wsm666\",\"password\":\"123456\"}")
    @PostMapping("/login")
    public ResultView login(@RequestBody String body) {
        JSONObject jsonObject = new JSONObject(body);
        String name = jsonObject.getString("name");
        String password = jsonObject.getString("password");

        //查账号是否存在
        EntityWrapper ew = new EntityWrapper();
        ew.eq("name", name);
        ew.eq("password", password);
        UserModel userModel = userService.selectOne(ew);
        if (userModel == null) {
            return ResultView.error(ResultEnum.CODE_5);
        }

        //查 OauthClientDetails 信息
        OauthClientDetailsModel oauthClientDetailsModel = oauthClientDetailsService.selectById(myConfig.getClientId());
        if (oauthClientDetailsModel == null) {
            ResultView.error(ResultEnum.CODE_7);
        }

        //获取token
        Map<String, String> tokenInfo = oauthFeign.getOauthToken(name, password, myConfig.getClientId(), oauthClientDetailsModel.getClientSecret(), "password", oauthClientDetailsModel.getScope());
        if (tokenInfo == null) {
            throw new MyRuntimeException(ResultView.hystrixError(MyConstant.wsm_oauth));
        }

        Map map = new HashMap();
        map.put("user", userModel);
        map.put("tokenInfo", tokenInfo);
        return ResultView.success(map);
    }

    @ApiOperation(value = "注销登录")
    @GetMapping("/logout")
    public ResultView logout(@RequestParam String token) {
        ResultView resultView = oauthFeign.logout(token);
        return resultView;
    }

    @ApiOperation(value = "查当前项目的资源角色信息")
    @GetMapping("/getResourceRoleInfo")
    public List<ResourceRoleInfoDto> getResourceRoleInfo(@RequestParam String projectCode) {
        List<ResourceRoleInfoDto> resourceRoleInfo = resourceRoleMapper.getResourceRoleInfo(projectCode);
        return resourceRoleInfo;
    }

    @ApiOperation(value = "根据client_id查询客户端resource_ids")
    @GetMapping("getResourceIdsByClientId")
    public String getResourceIdsByClientId(@RequestParam String clientId) {
        String resourceIds = oauthClientDetailsService.getResourceIdsByClientId(clientId);
        return resourceIds;
    }
}
