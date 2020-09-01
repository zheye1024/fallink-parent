package com.xiaozu.core.utils;

import com.xiaozu.core.exception.BusinessException;
import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @Author:80906
 * @Des:
 * @Date:2017/11/16
 */
public class ZMDateTimeUtil {
    /**
     * yyyy 年
     * MM 月
     * dd 天
     * HH 24小时制
     * mm 分钟
     * ss 秒
     */
    private final static String DEFAULT = "yyyy-MM-dd HH:mm:ss";

    /**
     * yyyyMMdd
     */
    public final static SimpleDateFormat sdfDays = new SimpleDateFormat("yyyyMMdd");
    public final static SimpleDateFormat DEFAULT_SDF = new SimpleDateFormat(DEFAULT);

    public static String formMatter(Date dateTime, String zone, String fmt) {
        ZoneId offset = ZoneId.systemDefault();
        try {
            offset = ZoneId.of(zone);
        } catch (Exception e) {
            //e.printStackTrace();
        }
        LocalDateTime localDateTime = LocalDateTime.ofInstant(dateTime.toInstant(), offset);
        return localDateTime.format(DateTimeFormatter.ofPattern(fmt == null ? DEFAULT : fmt));
    }


    /**
     * 根据时区偏移量格式化时间
     *
     * @param dateTime 当前时间
     * @param offset   偏移量
     * @return 返回格式化后的时间
     */
    public static String formMatter(Date dateTime, int offset) {
        return formMatter(dateTime, offset, DEFAULT);
    }

    public static String formMatter(Date dateTime, int offset, String fmt) {
        ZoneOffset zoneOffset = ZoneOffset.ofTotalSeconds(offset);
        return formMatter(dateTime, zoneOffset.getId(), fmt);
    }

    public static String formMatter(Date dateTime) {
        if (null == dateTime) {
            return "";
        }
        return formMatter(dateTime, DEFAULT);
    }

    public static String formMatter(Date dateTime, String fmt) {
        if (StringUtils.isBlank(fmt)) {
            fmt = DEFAULT;
        }
        if (dateTime == null) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(fmt);
        return sdf.format(dateTime);
    }


    /**
     * 获取一天最后时刻
     *
     * @param date   当前时间
     * @param offset 当前时间时区偏移量 北京时间28800
     * @return 返回 0:年月日00:00:00.000 1:年月日23:59:59.999
     */
    public static Date[] getStartAndEndDay(Date date, int offset) {
        Date end = getEndDayDate(date, offset);
        Date start = new Date(end.getTime() - 86400000 + 1000);
        Date[] startAndEndDay = new Date[2];
        startAndEndDay[0] = start;
        startAndEndDay[1] = end;
        return startAndEndDay;
    }

    public static String getTime(Date date, int offset) {
        ZoneOffset zoneOffset = ZoneOffset.ofTotalSeconds(offset);
        String fmt = "HH:mm:ss";
        return formMatter(date, zoneOffset.getId(), fmt);
    }

    public static Long getTime() {
        return System.currentTimeMillis();
    }

    public static Date getDate() throws BusinessException {
        return getDate(new Date());
    }

    public static Date getDate(Date date) {
        try {
            return DEFAULT_SDF.parse(DEFAULT_SDF.format(date));
        } catch (Exception e) {
            throw new BusinessException("500", "时间格式错误");
        }
    }

    private static Date getEndDayDate(Date date, int offset) {
        ZoneOffset zoneOffset = ZoneOffset.ofTotalSeconds(offset);
        LocalDateTime localDateTime = LocalDateTime.ofInstant(date.toInstant(), zoneOffset);
        localDateTime = localDateTime.withHour(23).withMinute(59).withSecond(59).withNano(999);
        Instant instant = localDateTime.atZone(zoneOffset).toInstant();
        return Date.from(instant);
    }

    private static Date getStartDayDate(Date date, int offset) {
        ZoneOffset zoneOffset = ZoneOffset.ofTotalSeconds(offset);
        LocalDateTime localDateTime = LocalDateTime.ofInstant(date.toInstant(), zoneOffset);
        localDateTime = localDateTime.withHour(0).withMinute(0).withSecond(0).withNano(001);
        Instant instant = localDateTime.atZone(zoneOffset).toInstant();
        return Date.from(instant);
    }

    public static Date plusDays(Date date, long days, int offset) {
        ZoneOffset zoneOffset = ZoneOffset.ofTotalSeconds(offset);
        LocalDateTime localDateTime = LocalDateTime.ofInstant(date.toInstant(), zoneOffset);
        localDateTime = localDateTime.plusDays(days);
        Instant instant = localDateTime.atZone(zoneOffset).toInstant();
        return Date.from(instant);
    }

    public static Date plusHours(Date date, long hours, int offset) {
        ZoneOffset zoneOffset = ZoneOffset.ofTotalSeconds(offset);
        LocalDateTime localDateTime = LocalDateTime.ofInstant(date.toInstant(), zoneOffset);
        localDateTime = localDateTime.plusHours(hours);
        Instant instant = localDateTime.atZone(zoneOffset).toInstant();
        return Date.from(instant);
    }


    public static String getWeekOfDate(Date dt) {
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }

    public static Set<String> getWeekAddDay(int day, List<String> weeks) {
        List<String> weekList = new ArrayList<>();
        weekList.add("星期一");
        weekList.add("星期二");
        weekList.add("星期三");
        weekList.add("星期四");
        weekList.add("星期五");
        weekList.add("星期六");
        weekList.add("星期日");

        Set<String> newWeekList = new LinkedHashSet<>();
        for (String newWeek : weeks) {
            newWeekList.add(newWeek);
            for (int i = 1; i <= day; i++) {
                int index = weekList.indexOf(newWeek) + i;
                if (index >= 7) {
                    newWeekList.add(weekList.get(index % 7));
                } else {
                    newWeekList.add(weekList.get(index));
                }
            }
        }
        return newWeekList;
    }

    public static String date2StringHM(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String dateString = formatter.format(date);
        return dateString;
    }

    public static String date2StringHMS(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(date);
        return dateString;
    }

    public static String date2StringYMD(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(date);
        return dateString;
    }

    /**
     * 获取YYYYMMDD格式
     *
     * @return
     */
    public static String getDays() {
        return sdfDays.format(new Date());
    }
}
