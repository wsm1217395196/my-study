package com.study.utils;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.util.HashSet;
import java.util.Set;

public class MyUtils {

    /**
     * 获取对象为空的属性名
     *
     * @param source          数据源
     * @param isEmptyProperty 获取是否为""的属性名
     * @param isNullProperty  获取是否为null的属性名
     * @return
     */
    public static String[] getNullPropertyNames(Object source, boolean isNullProperty, boolean isEmptyProperty) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<String>();
        for (java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (isNullProperty && srcValue == null) {
                emptyNames.add(pd.getName());
            }
            if (isEmptyProperty && srcValue == "") {
                emptyNames.add(pd.getName());
            }
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

}
