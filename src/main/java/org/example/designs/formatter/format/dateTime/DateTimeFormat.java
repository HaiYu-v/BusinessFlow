package org.example.designs.formatter.format.dateTime;

import org.example.designs.formatter.FormatException;
import org.example.designs.formatter.format.AbsFormat;
import org.example.designs.formatter.format.IFormat;
import org.example.designs.formatter.util.DateTimeUtil;

import java.sql.Time;
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
public class DateTimeFormat extends AbsFormat<DateTimeFormat, LocalDateTime> {

    private String strFormat = "yyyy-MM-dd HH:mm:ss";

    //时区(默认中国)
    private ZoneId zone = ZoneId.of("Asia/Shanghai");
    //日期和时间使用T分隔
    private boolean hasT = false;
    //不需要‘-’，连接号
    private boolean unHasDash = false;

    private DateTimeFormat(){}

    public static DateTimeFormat build(){
        return new DateTimeFormat()
                .min(LocalDateTime.MIN)
                .max(LocalDateTime.MAX)
                .defaultValue(LocalDateTime.now());
    }

    @Override
    public String toStr(Object data) throws FormatException {
        String ret = null;
        LocalDateTime localDate = format(data);
        try {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(strFormat);
            ret = localDate.format(dateFormatter);
        } catch (Exception e) {
            throw new FormatException(String.format("日期格式[%s]无效",strFormat),e);
        }
        return ret;
    }

    @Override
    public LocalDateTime format(Object data) throws FormatException {
        LocalDateTime ret = defaultValue;
        try {
            if(data instanceof Integer){
                ret = formatInt((Integer)data);
            }else if(data instanceof Long) {
                ret = formatLong((Long) data);
            }else if(data instanceof String){
                String str = (String) data;
                if(!str.isEmpty()) ret = formatString(str);
            }else if(data instanceof LocalTime){
                ret = formatLocalTime((LocalTime) data);
            } else if(data instanceof Time){
                ret = formatTime((Time) data);
            }else if(data instanceof LocalDateTime){
                ret = formatLocalDateTime((LocalDateTime) data);
            }else if(data instanceof Timestamp){
                ret = formatTimestamp((Timestamp) data);
            }else if(data instanceof Instant){
                ret = formatInstant((Instant) data);
            }else if(data instanceof Duration){
                ret = formatDuration((Duration) data);
            }else if(data instanceof LocalDate){
                ret = formatLocalDate((LocalDate) data);
            } else if(data instanceof Date){
                ret = formatDate((Date) data);
            } else if(data instanceof ZonedDateTime){
                ret = formatZonedDateTime((ZonedDateTime) data);
            }else {
                if(null == data && butNull){
                    return null;
                }
                throw new FormatException(String.format("不支持类型[%s]",(null == data?"null":data.getClass().getName())));
            }

        } catch (Exception e) {
            throw new FormatException(String.format("[%s]转换失败",data),e);
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

    private LocalDateTime formatString(String str) throws FormatException {

        try {
            if(DateTimeUtil.isTime(str) || DateTimeUtil.isTimeSeparator(str)){
                return formatLocalTime(DateTimeUtil.toLocalTime(str));
            }

            if(DateTimeUtil.isDate(str) || DateTimeUtil.isDateSeparator(str)){
                return formatLocalDate(DateTimeUtil.toLocalDate(str));
            }

            if(DateTimeUtil.isDateTime(str) || DateTimeUtil.isDateTimeSeparator(str)){
                return formatLocalDateTime(DateTimeUtil.toLocalDateTime(str));
            }
        } catch (Exception e) {
            throw new FormatException(String.format("[%s]不是有效的时间字符串",str),e);
        }
        throw new FormatException(String.format("[%s]不是有效的时间字符串",str));
    }
    private LocalDateTime formatLong(Long data) throws FormatException {
        return formatInstant(Instant.ofEpochMilli(data));
    }

    private LocalDateTime formatZonedDateTime(ZonedDateTime data) throws FormatException{
        return data.toLocalDateTime();
    }
    private LocalDateTime formatInt(Integer data) throws FormatException{
        return formatString(data.toString());
    }

    private LocalDateTime formatTimestamp(Timestamp data) throws FormatException{
        return formatInstant(data.toInstant());
    }

    private LocalDateTime formatDate(Date data) throws FormatException{
        return formatInstant(data.toInstant());
    }

    private LocalDateTime formatInstant(Instant data) throws FormatException{
        return formatZonedDateTime(data.atZone(zone));
    }

    private LocalDateTime formatLocalDateTime(LocalDateTime data) throws FormatException{
        return formatZonedDateTime(data.atZone(zone));
    }
    private LocalDateTime formatLocalTime(LocalTime data) throws FormatException{
        return formatLocalDateTime(LocalDateTime.of(LocalDate.now(),data));
    }
    private LocalDateTime formatTime(Time time) throws FormatException {
        return formatLocalTime(time.toLocalTime());
    }
    private LocalDateTime formatLocalDate(LocalDate data) throws FormatException{
        return formatLocalDateTime(LocalDateTime.of(data,LocalTime.MIDNIGHT));
    }

    private LocalDateTime formatDuration(Duration data)  throws FormatException {
        LocalTime startTime = LocalTime.MIDNIGHT;
        return formatLocalTime(startTime.plus(data));
    }

    public DateTimeFormat strFormat(String strFormat){
        this.strFormat = strFormat;
        return this;
    }
}
