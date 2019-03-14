package haituntiandi.com.haituntiandi.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间工具类
 */
public class TimeUtils {
    public static final String TIME_TYPE_01 = "yyyy-MM-dd HH:mm:ss";
    public static final String TIME_TYPE_02 = "yyyy-MM-dd";
    public static final String TIME_TYPE_03 = "yyyy年MM月dd日";
    public static final String TIME_TYPE_04 = "yyyy年MM月dd日 HH时mm分";
    public static final String TIME_TYPE_05 = "yyyy-MM-dd HH:mm";
    public static final String TIME_TYPE_06 = "MM-dd HH:mm:ss:SSS";
    public static final String TIME_TYPE_07 = "yyyy.MM.dd";

    /**
     * 时间戳转换成字符窜
     */
    public static String getDateToString(long time, String type) {
        Date d = new Date(time);
        SimpleDateFormat sf = new SimpleDateFormat(type);
        return sf.format(d);
    }

    /**
     * 将字符串转为时间戳
     */
    public static long getStringToDate(String time) {
        return getStringToDate(time, TIME_TYPE_01);
    }

    public static long getStringToDate(String time, String type) {
        SimpleDateFormat sdf = new SimpleDateFormat(type);
        Date date = new Date();
        try {
            date = sdf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime();
    }

    /**
     * 时间间隔计算
     *
     * @param time 时间戳 10位
     * @return
     */
    public static String getInterval(long time) {

        String interval = "";

        //间隔时间（秒）
        long betweenTime = (System.currentTimeMillis() - time * 1000) / 1000;
        if (betweenTime < 0) {
            interval = "刚刚";
        } else if (betweenTime >= 0 && betweenTime < 10) {
            interval = "刚刚";
        } else if (betweenTime >= 10 && betweenTime < 60) {
            interval = "刚刚";
        } else if (betweenTime >= 60 && betweenTime < 3600) {
            interval = betweenTime / 60 + "分钟前";
        } else if (betweenTime >= 3600 && betweenTime < 3600 * 24) {
            interval = betweenTime / 3600 + "小时前";
        } else if (betweenTime >= 3600 * 24 && betweenTime < 3600 * 24 * 2) {
            interval = "1天前";
        } else if (betweenTime >= 3600 * 24 * 2 && betweenTime < 3600 * 24 * 30) {
            interval = betweenTime / (3600 * 24) + "天前";
        } else if (betweenTime >= 3600 * 24 * 30) {
            interval = "30天以上";
        }
        return interval;
    }

    /**
     * 返回该月的天数
     *
     * @return
     */
    public static int getMonthCount(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);//先指定年份
        calendar.set(Calendar.MONTH, month - 1);//再指定月份 Java月份从0开始算
        return calendar.getActualMaximum(Calendar.DATE);//获取指定年份中指定月份有几天
    }


    /**
     * 获取系统时间戳
     *
     * @return
     */
    public static String getCurrentTime() {
        long time = System.currentTimeMillis();//获取系统时间的10位的时间戳
        String str = String.valueOf(time);
        return str;
    }


    public static int getDay(String weekday) {
        int day = 0;
        if ("星期日".equals(weekday)) {
            day = 1;
        } else if ("星期一".equals(weekday)) {
            day = 2;
        } else if ("星期二".equals(weekday)) {
            day = 3;
        } else if ("星期三".equals(weekday)) {
            day = 4;
        } else if ("星期四".equals(weekday)) {
            day = 5;
        } else if ("星期五".equals(weekday)) {
            day = 6;
        } else if ("星期六".equals(weekday)) {
            day = 7;
        }
        return day;
    }

    public static String getWeekOfDate(Date date) {
        String[] weekOfDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar calendar = Calendar.getInstance();
        if (date != null) {
            calendar.setTime(date);
        }
        int w = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0) {
            w = 0;
        }
        return weekOfDays[w];
    }

}
