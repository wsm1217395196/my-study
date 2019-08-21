package com.study.utils;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间工具类
 */
public class DateUtil {


    /**
     * yyyy-MM-dd HH:mm:ss转date类型
     */
    public static Date formatDate(String stringDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = sdf.parse(stringDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * date类型转yyyy-MM-dd HH:mm:ss
     */
    public static String formatDate(Date date) {
        Format format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String stringDate = format.format(date);
        return stringDate;
    }

    /**
     * "2018-03-01T16:00:00.000Z"格式转换成date类型。
     */
    public static Date formatDate_Z(String stringDate) {
        //注意是空格+UTC
        stringDate = stringDate.replace("Z", " UTC");
        //注意格式化的表达式
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");
        Date date = null;
        try {
            date = format.parse(stringDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 时间相加减转yyyy-MM-dd HH:mm:ss
     *
     * @param date
     * @param str  x年 或者x个月 或者x日/天
     * @return
     */
    public static String addTime(Date date, String str) {
        int size = 0;
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);//设置起时间
        if (str.contains("年")) {
            size = Integer.parseInt(str.substring(0, str.length() - 1));
            cal.add(Calendar.YEAR, size);//增加年
        }
        if (str.contains("个月")) {
            size = Integer.parseInt(str.substring(0, str.length() - 2));
            cal.add(Calendar.MONTH, size);//增加月
        }
        if (str.contains("日") || str.contains("天")) {
            size = Integer.parseInt(str.substring(0, str.length() - 1));
            cal.add(Calendar.DATE, size);//增加日/天
        }
        date = cal.getTime();
        Format format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }

    /**
     * date2比date1多的天数
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int differentDays(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        int day1 = cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);

        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        if (year1 != year2)   //同一年
        {
            int timeDistance = 0;
            for (int i = year1; i < year2; i++) {
                if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0)    //闰年
                {
                    timeDistance += 366;
                } else    //不是闰年
                {
                    timeDistance += 365;
                }
            }

            return timeDistance + (day2 - day1);
        } else    //不同年
        {
            System.out.println("判断day2 - day1 : " + (day2 - day1));
            return day2 - day1;
        }
    }

    /**
     * date2比date1多的秒数
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int differentSecond(Date date1, Date date2) {
        long time = date2.getTime() - date1.getTime();
        int second = (int) time / 1000;
        return second;
    }
}
