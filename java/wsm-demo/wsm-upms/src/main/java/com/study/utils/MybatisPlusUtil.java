package com.study.utils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.study.exception.MyRuntimeException;
import org.json.JSONObject;
import org.springframework.util.StringUtils;

/**
 * MybatisPlus相关工具类
 */
public class MybatisPlusUtil {

    /**
     * 排序处理
     *
     * @param qw
     * @param sortStr
     */
    public static void sortUtil(QueryWrapper qw, String sortStr) {
        if (!StringUtils.isEmpty(sortStr)) {
            try {
                String[] sorts = sortStr.split(",");
                for (String s : sorts) {
                    s = s.trim();
                    String[] s1 = s.split("-");
                    String column = s1[0].trim();
                    String sort = s1[1].trim();
                    if (sort.equalsIgnoreCase("asc")) {
                        qw.orderByAsc(StringUtil.addBackquote(column));
                    } else {
                        qw.orderByDesc(StringUtil.addBackquote(column));
                    }
                }
            } catch (Exception e) {
                throw new MyRuntimeException("排序格式不对！");
            }
        }
    }

    /**
     * eq处理
     *
     * @param qw
     * @param object
     * @param columns
     */
    public static void eqUtil(QueryWrapper qw, JSONObject object, String[] columns) {
        for (String column : columns) {
            String value = object.getString(column).trim();
            if (!StringUtils.isEmpty(value)) {
                column = HumpUtil.toUnderline(column);
                qw.eq(StringUtil.addBackquote(column), value);
            }
        }
    }

    /**
     * like处理
     *
     * @param qw
     * @param object
     * @param columns
     */
    public static void likeUtil(QueryWrapper qw, JSONObject object, String[] columns) {
        for (String column : columns) {
            String value = object.getString(column).trim();
            if (!StringUtils.isEmpty(value)) {
                column = HumpUtil.toUnderline(column);
                qw.like(StringUtil.addBackquote(column), value);
            }
        }
    }
}
