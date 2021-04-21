package com.antplatform.admin.common.utils;

import com.antplatform.admin.common.base.Constant;
import lombok.extern.slf4j.Slf4j;

import java.lang.management.ManagementFactory;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author: maoyan
 * @date: 2021/3/30 10:37:46
 * @description:
 */
@Slf4j
public class DataUtil {
    public static final String DATE_FORMAT = "yyyy-MM-dd";

    public static final String DATE_TIME_HOUR_MINUTE_SECOND = "yyyy-MM-dd HH:mm:ss";

    public static final String DATE_TIME_HOUR_MINUTE_FORMAT = "yyyy-MM-dd HH:mm";

    public static final String TIME_FORMAT = "HH:mm:ss";

    /**
     * 获取服务器启动时间
     */
    public static Date getServerStartDate() {
        long time = ManagementFactory.getRuntimeMXBean().getStartTime();
        return new Date(time);
    }

    /**
     * 获取当前Date型日期
     *
     * @return Date() 当前日期
     */
    public static Date getNowDate() {
        return new Date();
    }


    /**
     * 根据format格式生成当前日期
     *
     * @param format
     * @return
     */
    public static String getTodayTime(String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);

        return simpleDateFormat.format(new Date());
    }

    public static String getTime(Date date,String format){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);

        return simpleDateFormat.format(date);
    }

    /**
     * 根据format格式生成当前日期，默认格式yyyy-MM-dd HH:mm:ss
     *
     * @param format
     * @return
     */
    public static String getTodayTime(String... format) {
        Date currentTime = new Date();
        SimpleDateFormat simpleDateFormat;
        if (format == null || format.length <= 0) {
            simpleDateFormat = new SimpleDateFormat(DATE_TIME_HOUR_MINUTE_SECOND);
        } else {
            simpleDateFormat = new SimpleDateFormat(format[0]);
        }
        return simpleDateFormat.format(currentTime);
    }

    /**
     * 根据format格式生成指定日期，默认格式yyyy-MM-dd HH:mm:ss
     *
     * @param update +:取当前日期的后n天 -:取当前日期的前n天
     * @param format
     * @returngetTime
     */
    public static String getTime(int update, String... format) {
        SimpleDateFormat simpleDateFormat;
        if (format == null || format.length <= 0) {
            simpleDateFormat = new SimpleDateFormat(DATE_TIME_HOUR_MINUTE_SECOND);
        } else {
            simpleDateFormat = new SimpleDateFormat(format[0]);
        }

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, update);

        return simpleDateFormat.format(calendar.getTime());
    }

    /**
     * 获取指定日期的前/后update日期
     *
     * @param time
     * @param update
     * @param flag   不传 默认时间；true 获取23:59；false 获取00:00
     * @return
     */
    public static String getTime(String time, int update, Boolean... flag) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_TIME_HOUR_MINUTE_FORMAT);
        // 将字符串的日期转为Date类型，ParsePosition(0)表示从第一个字符开始解析
        Date date = simpleDateFormat.parse(time, new ParsePosition(0));

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, update);

        if (flag.length > 0 && flag[0]) {
            calendar.set(Calendar.HOUR_OF_DAY, 23);
            calendar.set(Calendar.MINUTE, 59);
        }
        if (flag.length > 0 && !flag[0]) {
            calendar.set(Calendar.HOUR_OF_DAY, 00);
            calendar.set(Calendar.MINUTE, 00);
        }
        return simpleDateFormat.format(calendar.getTime());
    }

    /**
     * 获取指定日期的星期几
     *
     * @param time
     * @return
     */
    public static String getWeek(String time) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_TIME_HOUR_MINUTE_FORMAT);
        Date date = simpleDateFormat.parse(time, new ParsePosition(0));
        simpleDateFormat = new SimpleDateFormat("E");
        String week = simpleDateFormat.format(date);
        return "周" + week.substring(week.length() - 1);
    }

    /**
     * 获取带有星期的日期
     *
     * @param time
     * @return
     */
    public static String getTimeWithWeek(String time) {
        String week = getWeek(time);
        String[] times = time.split(" ");

        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(times[0] + " " + week + " " + times[1]);
        return stringBuffer.toString();
    }

    /**
     * 获取一定范围的日期
     *
     * @param time
     * @return
     */
    public static String getTimeWithRange(String time) {
        String afterTime = getTime(time, 1);
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(time.split(" ")[0] + "~" + afterTime.split(" ")[0]);
        return stringBuffer.toString();
    }

    /**
     * 格式化time
     *
     * @param time
     * @return
     */
    public static String getTimeFormat(String time) {
        if (time.contains("周")) {
            String[] times = time.split(" ");
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(times[0] + " " + times[2]);
            return stringBuffer.toString();
        }
        if (time.contains("~")) {
            String[] times = time.split("~");
            return times[1] + " 00:00";
        }
        return "";
    }

    /**
     * 时间戳转时间
     *
     * @param timeStamp
     * @return
     */
    public static String timeStampToDate(String timeStamp) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_TIME_HOUR_MINUTE_SECOND);
        long lt = new Long(timeStamp);
        String date = simpleDateFormat.format(lt);
        return date;
    }

    /**
     * 时间转时间戳
     *
     * @param time
     * @return
     */
    public static String dateToTimeStamp(String time) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_TIME_HOUR_MINUTE_SECOND);
        Date date = simpleDateFormat.parse(time, new ParsePosition(0));
        return String.valueOf(date.getTime());
    }

    /**
     * 判断字符串是否为有效的日期格式
     *
     * @param str
     * @return true 时间格式 是；false 时间戳 否
     */
    private static boolean isValidDate(String str) {
        boolean convertSuccess = true;
        SimpleDateFormat format = new SimpleDateFormat(DATE_TIME_HOUR_MINUTE_SECOND);
        try {
            format.setLenient(false);
            format.parse(str);
        } catch (ParseException e) {
            convertSuccess = false;
        }
        return convertSuccess;
    }

    /**
     * 时间比较
     *
     * @param time1
     * @param time2
     * @return ture:time1<=time2 false:time1>time2
     */
    public static Boolean timeCompare(String time1, String time2) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_TIME_HOUR_MINUTE_SECOND);

        if (!isValidDate(time1)) {
            time1 = timeStampToDate(time1);
        }
        if (!isValidDate(time2)) {
            time2 = timeStampToDate(time2);
        }
        Date date1 = simpleDateFormat.parse(time1, new ParsePosition(0));
        Date date2 = simpleDateFormat.parse(time2, new ParsePosition(0));

        return date1.before(date2) || date1.equals(date2);
    }

    /**
     * 判断时间是否在时间段内
     * 传入的时间格式要和格式化时间对应一致 例如start为 13:35:10  则时间格式pattern为HH:mm:ss 以此类推
     *
     * @param start 开始时间
     * @param end   结束时间
     * @return
     */
    public static boolean isInTime(String start, String end) {
        SimpleDateFormat format = new SimpleDateFormat(TIME_FORMAT);
        String now = format.format(new Date());

        Date nowTime = format.parse(now, new ParsePosition(0));
        Date startTime = format.parse(start, new ParsePosition(0));
        Date endTime = format.parse(end, new ParsePosition(0));

        return startTime.before(nowTime) && endTime.after(nowTime);
    }

    public static String getDatePoor(Date startTime, Date endTime, Constant.TimeType timeType) {
        // 一天的毫秒数
        long nd = 1000 * 24 * 60 * 60;
        // 一小时的毫秒数
        long nh = 1000 * 60 * 60;
        // 一分钟的毫秒数
        long nm = 1000 * 60;
        // 一秒钟的毫秒数
        long ns = 1000;
        long diff;
        long day;
        long hour;
        long min;
        long sec;
        // 获得两个时间的毫秒时间差异
        diff = endTime.getTime() - startTime.getTime();
        // 计算差多少天
        day = diff / nd;
        // 计算差多少小时
        hour = diff % nd / nh;
        // 计算差多少分钟
        min = diff % nd % nh / nm;
        // 计算差多少秒
        sec = diff % nd % nh % nm / ns;
        // 输出结果
        log.info("时间相差: {}天{}小时{}分钟{}秒。",day,hour,min,sec);

        if (timeType.equals(Constant.TimeType.DAY)) {
            return day + timeType.getName();
        }
        if (timeType.equals(Constant.TimeType.HOUR)) {
            return hour + timeType.getName();
        }
        return null;
    }

    public static void main(String[] args) {
        long time1 = 1617026634667L;
        Date startData = new Date(time1);
        Date endData = new Date();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_TIME_HOUR_MINUTE_SECOND);
        System.out.println(simpleDateFormat.format(startData));
        System.out.println(simpleDateFormat.format(endData));

        System.out.println(getDatePoor(startData, endData, Constant.TimeType.HOUR));
    }
}
