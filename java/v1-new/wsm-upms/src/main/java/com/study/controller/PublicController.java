package com.study.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.study.MyConstant;
import com.study.exception.MyRuntimeException;
import com.study.feign.OauthFeign;
import com.study.model.OauthClientDetailsModel;
import com.study.model.UserModel;
import com.study.result.ResultEnum;
import com.study.result.ResultView;
import com.study.service.OauthClientDetailsService;
import com.study.service.UserService;
import com.study.utils.CreateUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 公共控制器
 */
@Api(description = "公共控制器")
@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private UserService userService;
    @Autowired
    private OauthClientDetailsService oauthClientDetailsService;
    @Autowired
    private OauthFeign oauthFeign;
    @Autowired
    private RestTemplate restTemplate;

    @ApiOperation(value = "注册", notes = "提交参数：{\"name\":\"wsm666\",\"password\":\"123456\"}")
    @PostMapping("/register")
    public ResultView register(@RequestBody JSONObject jsonObject) {
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
    public ResultView login(@RequestBody JSONObject jsonObject) {
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
        OauthClientDetailsModel oauthClientDetailsModel = oauthClientDetailsService.selectById(MyConstant.ResourceAauth);
        if (oauthClientDetailsModel == null) {
            ResultView.error(ResultEnum.CODE_7);
        }

        //获取token
        Map<String, String> tokenInfo = oauthFeign.getOauthToken(name, password, MyConstant.ResourceAauth, oauthClientDetailsModel.getClientSecret(), "password", oauthClientDetailsModel.getScope());
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

    @ApiOperation(value = "根据client_id查询客户端resource_ids")
    @GetMapping("/getResourceIdsByClientId")
    public String getResourceIdsByClientId(@RequestParam String clientId) {
        String resourceIds = oauthClientDetailsService.getResourceIdsByClientId(clientId);
        return resourceIds;
    }

    @ApiOperation(value = "根据地址刷新各服务权限")
    @GetMapping("/loadResourceDefine")
    public ResultView loadResourceDefine(@ApiParam("服务器地址,默认为localhost") @RequestParam(required = false, defaultValue = "localhost") String serverUrl, @ApiParam("服务名,多个用逗号隔开") @RequestParam String[] serverNames) {
        if (serverNames.length == 0) {
            return ResultView.error("请填写服务名！");
        }
        for (String serverName : serverNames) {
            if (serverName.equals(MyConstant.wsm_upms)) {
//                mySecurityMetadataSource.loadResourceDefine();
            } else {
                try {
                    String url = "http://" + serverUrl + ":8001/" + serverName + "/public/loadResourceDefine";
                    restTemplate.getForEntity(url, ResultView.class);
                } catch (RestClientException e) {
                    return ResultView.error("请填写正确的地址和服务名！");
                }
            }
        }
        return ResultView.success();
    }
}
