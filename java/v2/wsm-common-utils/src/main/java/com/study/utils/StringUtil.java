package com.study.utils;

/**
 * String相关工具类
 */
public class StringUtil {

    /**
     * 给字符串加反引号
     *
     * @return
     */
    public static String addBackquote(String str) {
        return "`" + str + "`";
    }
}
