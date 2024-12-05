package org.example.designs.formatter.format.dateTime;

import org.example.designs.formatter.FormatException;
import org.example.designs.formatter.format.IFormat;

import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;


/**
 * 日期格式器
 *
 * <p>
 *     1.年，月，日，时，分，秒
 *     2.年月日是否要[-]
 *     3.时分秒是否要[:]
 *     4.是否要转成字符串
 * </p>
 *
 * @author HaiYu
 * @version 1.0.0
 * @date 2024-12-02
 */
public class DateTimeFormat implements IFormat<Object, LocalDateTime> {
    private

    //可以为null
    private boolean butNull = false;
    //支持字符串
    private boolean unString = false;
    //最大值
    private LocalDateTime max = LocalDateTime.MAX;
    //最小值
    private LocalDateTime min = LocalDateTime.MIN;
    //默认值
    private LocalDateTime defaultValue = LocalDateTime.now();
    //时区(默认中国)
    private ZoneId zone = ZoneId.of("Asia/Shanghai");
    //日期和时间使用T分隔
    private boolean hasT = false;
    //不需要‘-’，连接号
    private boolean unHasDash = false;

    private DateTimeFormat() {
    }

    public static DateTimeFormat build() {
        return new DateTimeFormat();
    }

    @Override
    public LocalDateTime format(Object data) throws FormatException {
        LocalDateTime ret = defaultValue;

        if(data instanceof Integer) {
            ret = formatInt((Integer) data);
        }else if(data instanceof Long){
            ret = formatLong((Long) data);
        }else if(data instanceof String){
            String str = (String) data;
            if(!str.isEmpty()) ret = formatString(str);
        } else if (data instanceof LocalDate) {
            ret = formatLocalDate((LocalDate) data);
        } else if (data instanceof LocalTime) {
            ret = formatLocalTime((LocalTime) data);
        } else if (data instanceof LocalDateTime) {
            ret = formatLocalDateTime((LocalDateTime) data);
        } else if (data instanceof ZonedDateTime) {
            ret = formatZonedDateTime((ZonedDateTime) data);
        } else if (data instanceof Instant) {
            ret = formatInstant((Instant) data);
        }else if(data instanceof Timestamp){
            ret = formatTimestamp((Timestamp) data);
        } else if (data instanceof Date) {
            ret = formatDate((Date) data);
        }else {
            throw new FormatException(String.format("不支持类型[%s]",data.getClass().getName()));
        }

        //判断为空
        if(null == ret && !butNull)
            throw new FormatException("数据为null且无默认值");
        if(ret.isBefore(min))
            throw new FormatException(String.format("[%s]小于最小值[%s]",ret,min));
        if(ret.isAfter(max))
            throw new FormatException(String.format("[%s]大于最大值[%s]",ret,max));
        return ret;
    }

    private LocalDateTime formatLong(Long data) throws FormatException {
        return formatInstant(Instant.ofEpochSecond(data));
    }

    private LocalDateTime formatZonedDateTime(ZonedDateTime data) throws FormatException{
        return data.toLocalDateTime();
    }

    private LocalDateTime formatString(String str) throws FormatException {
        // 正则表达式
        String datePattern = "^\\d{4}\\d{2}\\d{2}$"; // yyyyMMdd
        String dateWithDashPattern = "^\\d{4}-\\d{2}-\\d{2}$"; // yyyy-MM-dd
        String timePattern = "^([01]?[0-9]|2[0-3])([0-5]?[0-9])([0-5]?[0-9])$"; // HHmmss
        String timeWithColonPattern = "^([01]?[0-9]|2[0-3]):([0-5]?[0-9]):([0-5]?[0-9])$"; // HH:mm:ss
        String dateTimePattern = "^\\d{4}\\d{2}\\d{2} \\d{6}$"; // yyyyMMdd HHmmss
        String dateTimeWithDashPattern = "^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}$"; // yyyy-MM-dd HH:mm:ss



        // 定义格式化器
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        DateTimeFormatter dateWithDashFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HHmmss");
        DateTimeFormatter timeWithColonFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd HHmmss");
        DateTimeFormatter dateTimeWithDashFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // 根据输入字符串匹配对应的格式并解析为 LocalDateTime
        if (input.matches(datePattern)) {
            return LocalDateTime.parse(input + "000000", dateTimeFormatter); // 日期+时间补零
        } else if (input.matches(dateWithDashPattern)) {
            return LocalDateTime.parse(input + " 000000", dateTimeWithDashFormatter); // 日期+时间补零
        } else if (input.matches(timePattern)) {
            return LocalDateTime.parse("1970-01-01 " + input, dateTimeFormatter); // 时间+默认日期
        } else if (input.matches(timeWithColonPattern)) {
            return LocalDateTime.parse("1970-01-01 " + input, dateTimeWithDashFormatter); // 时间+默认日期
        } else if (input.matches(dateTimePattern)) {
            return LocalDateTime.parse(input, dateTimeFormatter); // 日期时间格式
        } else if (input.matches(dateTimeWithDashPattern)) {
            return LocalDateTime.parse(input, dateTimeWithDashFormatter); // 日期时间格式
        } else {
            throw new IllegalArgumentException("Invalid date/time format");
        }
    }

    private LocalDateTime formatInt(Integer data) throws FormatException{
        return formatString(data.toString());
    }

    private LocalDateTime formatTimestamp(Timestamp data) throws FormatException{
        return formatInstant(data.toInstant());
    }

    private LocalDateTime formatDate(Date data) throws FormatException{
        return formatZonedDateTime(ZonedDateTime.ofInstant(data.toInstant(), zone));
    }

    private LocalDateTime formatInstant(Instant data) throws FormatException{
        return formatZonedDateTime(ZonedDateTime.ofInstant(data, zone));
    }

    private LocalDateTime formatLocalDateTime(LocalDateTime data) throws FormatException{
        return formatZonedDateTime(data.atZone(zone));
    }
    private LocalDateTime formatLocalTime(LocalTime data) throws FormatException{
        return formatZonedDateTime(ZonedDateTime.from(data));
    }

    private LocalDateTime formatLocalDate(LocalDate data) throws FormatException{
        return formatZonedDateTime(data.atStartOfDay(zone));
    }


}
