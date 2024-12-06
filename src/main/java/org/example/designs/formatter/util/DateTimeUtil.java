package org.example.designs.formatter.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

/**
 * TODO
 *
 * <p>
 *     TODO
 * </p>
 *
 * @author HaiYu
 * @version 1.0.0
 * @date 2024-12-06
 */
public class DateTimeUtil {
    private static final String TIME_PATTERN
            = "^([01]\\d|2[0-3])([0-5]\\d)(([0-5]\\d)(\\d{3})?)?$";
    private static final String TIME_PATTERN_SEPARATOR
            = "^([01]\\d|2[0-3]):([0-5]\\d)(:([0-5]\\d)(\\.\\d{3})?)?$";

    private static final String DATE_PATTERN
            = "^(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]|[0-9][1-9][0-9]{2}|[1-9][0-9]{3})(0[1-9]|1[0-2])(0[1-9]|[12]\\d|3[01]))$";

    private static final String DATE_PATTERN_SEPARATOR
            = "^(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]|[0-9][1-9][0-9]{2}|[1-9][0-9]{3})-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))$";

    private static final String DATETIME_PATTERN
            = "^(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]|[0-9][1-9][0-9]{2}|[1-9][0-9]{3})(0[1-9]|1[0-2])(0[1-9]|[12]\\d|3[01]))" +
            "[ T]?([01]\\d|2[0-3])([0-5]\\d)(([0-5]\\d)(\\d{3})?)?$"; // yyyyMMdd HHmmss或yyyyMMddTHHmmss
    private static final String DATETIME_PATTERN_SEPARATOR
            = "^(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]|[0-9][1-9][0-9]{2}|[1-9][0-9]{3})-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))" +
            "[ T]([01]\\d|2[0-3]):([0-5]\\d)(:([0-5]\\d)(\\.\\d{3})?)?$"; // yyyy-MM-ddTHH:mm:ss

    public static final String TIME_FORMAT = "HHmm[ss[SSS]]";
    public static final String TIME_FORMAT_SEPARATOR = "HH:mm[:ss[.SSS]]";
    public static final String DATE_FORMAT1 = "MMdd";
    public static final String DATE_FORMAT = "yyyyMMdd";
    public static final String DATE_FORMAT_SEPARATOR = "[yyyy-]MM-dd";
    public static final String DATETIME_FORMAT1 = "yyyyMMddHHmm";
    public static final String DATETIME_FORMAT2 = "yyyyMMddHHmmss";
    public static final String DATETIME_FORMAT3 = "yyyyMMddHHmmssSSS";
    public static final String DATETIME_FORMAT = "yyyyMMdd[ ]['T']HHmm[ss[SSS]]";
    public static final String DATETIME_FORMAT_SEPARATOR = "yyyy-MM-dd[ ]['T']HH:mm[:ss[.SSS]]";

    public static boolean isTime(String timeStr) {
        if(null == timeStr || timeStr.isEmpty()) return false;
        return Pattern.compile(TIME_PATTERN).matcher(timeStr).matches();
    }
    public static boolean isTimeSeparator(String timeStr) {
        if(null == timeStr || timeStr.isEmpty()) return false;
        return Pattern.compile(TIME_PATTERN_SEPARATOR).matcher(timeStr).matches();
    }
    public static boolean isDate(String dateStr) {
        if(null == dateStr || dateStr.isEmpty()) return false;
        return Pattern.compile(DATE_PATTERN).matcher(dateStr).matches()
                || isLeapYearDate(dateStr);
    }
    public static boolean isDateSeparator(String dateStr) {
        if(null == dateStr || dateStr.isEmpty()) return false;
        return Pattern.compile(DATE_PATTERN_SEPARATOR).matcher(dateStr).matches();
    }
    public static boolean isDateTime(String dateTimeStr) {
        if(null == dateTimeStr || dateTimeStr.isEmpty()) return false;
        return Pattern.compile(DATETIME_PATTERN).matcher(dateTimeStr).matches()
                || isLeapYearDate(dateTimeStr.substring(0,8));
    }
    public static boolean isDateTimeSeparator(String dateTimeStr) {
        if(null == dateTimeStr || dateTimeStr.isEmpty()) return false;
        return Pattern.compile(DATETIME_PATTERN_SEPARATOR).matcher(dateTimeStr).matches();
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * 将字符串解析为时间（LocalTime）
     *
     * @param timeStr
     * @return {@link LocalTime }
     */
    public static LocalTime toLocalTime(String timeStr){
        if(isTime(timeStr)){
            return LocalTime.parse(timeStr, DateTimeFormatter.ofPattern(TIME_FORMAT));
        }
        if(isTimeSeparator(timeStr)){
            return LocalTime.parse(timeStr, DateTimeFormatter.ofPattern(TIME_FORMAT_SEPARATOR));
        }
        return null;
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * 将字符串解析为日期（LocalDate）
     *
     * @param dateStr
     * @return {@link LocalTime }
     */
    public static LocalDate toLocalDate(String dateStr){
        if(isDate(dateStr)){
            if(dateStr.length() == 4)
                return LocalDate.parse(dateStr, DateTimeFormatter.ofPattern(DATE_FORMAT1));
            return LocalDate.parse(dateStr, DateTimeFormatter.ofPattern(DATE_FORMAT));
        }
        if(isDateSeparator(dateStr)){
            return LocalDate.parse(dateStr, DateTimeFormatter.ofPattern(DATE_FORMAT_SEPARATOR));
        }
        return null;
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * 将字符串解析为日期（LocalDateTime）
     *
     * @param dateTimeStr
     * @return {@link LocalTime }
     */
    public static LocalDateTime toLocalDateTime(String dateTimeStr){
        if(isDateTime(dateTimeStr)){
            if (dateTimeStr.length()==12)
                return LocalDateTime.parse(dateTimeStr, DateTimeFormatter.ofPattern(DATETIME_FORMAT1));
            if (dateTimeStr.length()==14)
                return LocalDateTime.parse(dateTimeStr, DateTimeFormatter.ofPattern(DATETIME_FORMAT2));
            if (dateTimeStr.length()==17)
                return LocalDateTime.parse(dateTimeStr, DateTimeFormatter.ofPattern(DATETIME_FORMAT3));
            return LocalDateTime.parse(dateTimeStr, DateTimeFormatter.ofPattern(DATETIME_FORMAT));
        }
        if(isDateTimeSeparator(dateTimeStr)){
            return LocalDateTime.parse(dateTimeStr, DateTimeFormatter.ofPattern(DATETIME_FORMAT_SEPARATOR));
        }
        return null;
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * TODO
     *
     * 有[-]作为分隔符的能自动判断是不是闰年，没有的需要用这个方法来判断
     *
     * @param dateStr
     * @return boolean
     */
    public static boolean isLeapYearDate(String dateStr) {
        if (dateStr == null || dateStr.length() != 8) {
            return false;  // 日期字符串长度必须为 8 位 (yyyyMMdd)
        }

        try {
            // 提取年份、月份和日期
            int year = Integer.parseInt(dateStr.substring(0, 4));
            int month = Integer.parseInt(dateStr.substring(4, 6));
            int day = Integer.parseInt(dateStr.substring(6, 8));

            // 判断是否是 2 月 29 日
            if (month == 2 && day == 29) {
                // 如果是 2 月 29 日，检查是否为闰年
                return isLeapYear(year);
            }

            // 如果不是 2 月 29 日，返回 false
            return false;

        } catch (NumberFormatException e) {
            return false;  // 如果解析失败，日期无效
        }
    }

    // 判断是否为闰年
    public static boolean isLeapYear(int year) {
        return (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0));
    }




}
