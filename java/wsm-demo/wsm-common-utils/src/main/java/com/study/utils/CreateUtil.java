package com.study.utils;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * 生成啥啥啥工具类
 */
public class CreateUtil {

    private static Format format = new SimpleDateFormat("yyMMddHHmm");
    private static Random random = new Random();
    private static int numValue = 0;
    private static int maxValue = 990000;

    /**
     * 生成id
     *
     * @return
     */
    public static Long id() {
        String value = format.format(new Date()) + random.nextInt(9) + random.nextInt(9) + random.nextInt(9);
        if (numValue > maxValue) {
            numValue = 0;
        }
        numValue++;
        value = value + String.format("%06d", numValue);//前面补0
        long id = Long.parseLong(value);
        return id;
    }


    /**
     * 生成id(9个线程以内)
     *
     * @param threadNum 第几个线程
     * @return
     */
    public static Long id(int threadNum) {
        String value = format.format(new Date()) + random.nextInt(9) + random.nextInt(9) + threadNum;
        if (numValue > maxValue) {
            numValue = 0;
        }
        numValue++;
        value = value + String.format("%06d", numValue);//前面补0
        long id = Long.parseLong(value);
        return id;
    }

    /**
     * 生成指定长度的数字验证码
     *
     * @param length
     * @return
     */
    public static String validateCode(int length) {
        String validateCode = "";
        for (int i = 0; i < length; i++) {
            validateCode += random.nextInt(9);
        }
        return validateCode;
    }
}
