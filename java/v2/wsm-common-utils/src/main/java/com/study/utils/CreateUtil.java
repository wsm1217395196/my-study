package com.study.utils;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * 生成啥啥啥工具类
 */
public class CreateUtil {

    /**
     * 生成id
     *
     * @return
     */
    public static Long id() {
        Format format = new SimpleDateFormat("yyyyMMddHHmmss");
        int a = (int) (Math.random() * 10.0D);
        int b = (int) (Math.random() * 10.0D);
        int c = (int) (Math.random() * 10.0D);
        int d = (int) (Math.random() * 10.0D);
        String date = format.format(new Date());
        StringBuffer sb = new StringBuffer();
        sb.append(date).insert(a, b);
        sb.insert(b, c);
        sb.insert(c, d);
        sb.insert(d, a);
        sb.insert(d, b);
        String createdId = sb.toString();
        return Long.parseLong(createdId);
    }

    /**
     * 生成指定长度的数字验证码
     *
     * @param length
     * @return
     */
    public static String validateCode(int length) {
        String validateCode = "";
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            validateCode += random.nextInt(9);
        }
        return validateCode;
    }

}
