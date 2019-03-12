package com.study.controller;

import com.study.currency.result.ResultEnum;
import com.study.currency.result.ResultView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@Api(description = "邮箱控制器")
@RestController
@RequestMapping("/email")
public class EmailController {

    @Value("${spring.mail.username}")
    private String emailFrom;
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 发送邮箱验证码
     *
     * @param email
     * @return
     */
    @ApiOperation(value = "发送邮箱验证码", notes = "")
    @GetMapping("/sendEmailCode")
    public ResultView sendEmailCode(String email) {
        String emailCode = "123456";
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(emailFrom);
        mailMessage.setTo(email);
        mailMessage.setSubject("wsm验证码");
        mailMessage.setText("你的邮箱验证码码为：" + emailCode + "，请于3分钟之内填写!");
        javaMailSender.send(mailMessage);
        redisTemplate.opsForValue().set(email, emailCode, 120, TimeUnit.SECONDS);
        return ResultView.success();
    }

    /**
     * 验证邮箱验证码
     *
     * @param email
     * @param emailCode
     * @return
     */
    @ApiOperation(value = "验证邮箱验证码", notes = "")
    @GetMapping("/validateEmailCode")
    public ResultView validateEmailCode(String email, String emailCode) {
        ResultView resultView = ResultView.error(ResultEnum.CODE_2);
        String redisEmailCode = (String) redisTemplate.opsForValue().get(email);
        if (redisEmailCode.equals(emailCode)) {
            resultView = ResultView.success();
            resultView.setMsg("验证码正确！");
        } else {
            resultView.setMsg("验证码错误！");
        }
        return resultView;
    }

}
