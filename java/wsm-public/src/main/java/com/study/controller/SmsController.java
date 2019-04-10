package com.study.controller;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.study.currency.result.ResultEnum;
import com.study.currency.result.ResultView;
import com.study.currency.utils.CreateUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(description = "短信控制器")
@RestController
public class SmsController {

    @Value("${wsm.name}")
    private String userName;

    @Autowired
    private RedisTemplate redisTemplate;

    @ApiOperation(value = "发送短信验证码", notes = "")
    @PostMapping("/sendSmsCode")
    public ResultView sendSmsCode(@RequestParam String phone) throws ClientException {
        String validateCode = CreateUtil.validateCode(6);
        String code = "{'code':'" + validateCode + "'}";

        DefaultProfile profile = DefaultProfile.getProfile("default", "LTAIHSxDPySInu5H", "PWCuoEglpXMkH6ZJcnYoxr89WUlSoF");
        IAcsClient client = new DefaultAcsClient(profile);
        CommonRequest request = new CommonRequest();

        request.setMethod(MethodType.POST);
        request.setDomain("wsm666.com");
        request.setVersion("666");
        //系统规定参数。取值：SendSms。
        request.setAction("SendSms");
        //接收短信的手机号码。
        request.putQueryParameter("PhoneNumbers", phone);
        //短信签名名称。
        request.putQueryParameter("SignName", "短信签名名称");
        //短信模板ID
        request.putQueryParameter("TemplateCode", "短信模板ID");
        //短信模板变量对应的实际值，JSON格式。{"code":"1111"}
        request.putQueryParameter("TemplateParam", code);
        //上行短信扩展码，无特殊需要此字段的用户请忽略此字段。
        request.putQueryParameter("SmsUpExtendCode", "SmsUpExtendCode");
        //外部流水扩展字段。
        request.putQueryParameter("OutId", "OutId");

        CommonResponse response = client.getCommonResponse(request);
        System.out.println(response.getData());

        return ResultView.success();
    }

    @ApiOperation(value = "验证短信验证码", notes = "")
    @PostMapping("/vaildateSmsCode")
    public ResultView vaildateSmsCode(@RequestParam String phone, @RequestParam String smsCode) {
        System.out.println(userName);
        ResultView resultView = ResultView.error(ResultEnum.CODE_4);
        String redisSmsCode = (String) redisTemplate.opsForValue().get(phone);
        if (redisSmsCode != null && redisSmsCode.equals(smsCode)) {
            resultView = ResultView.success();
            resultView.setMsg("验证码正确！");
        }
        return resultView;
    }

}
