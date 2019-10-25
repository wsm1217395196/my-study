package com.study.utils;

/**
 * 驼峰命名转换
 */
public class HumpUtil {

    /**
     * 驼峰命名转为下划线命名
     *
     * @param para 驼峰命名的字符串
     * @return
     */
    public static String toUnderline(String para) {
        StringBuilder sb = new StringBuilder(para);
        //定位
        int temp = 0;
        if (!para.contains("_")) {
            for (int i = 0; i < para.length(); i++) {
                if (Character.isUpperCase(para.charAt(i)) && i != 0) {
                    sb.insert(i + temp, "_");
                    temp += 1;
                }
            }
        }
        return sb.toString().toLowerCase();
    }

    /**
     * 下划线命名转为驼峰命名
     *
     * @param para 下划线命名的字符串
     * @return
     */
    public static String toHump(String para) {
        StringBuilder result = new StringBuilder();
        String a[] = para.split("_");
        for (String s : a) {
            if (!para.contains("_")) {
                result.append(s);
                continue;
            }
            if (result.length() == 0) {
                result.append(s.toLowerCase());
            } else {
                result.append(s.substring(0, 1).toUpperCase());
                result.append(s.substring(1).toLowerCase());
            }
        }
        return result.toString();
    }
}
