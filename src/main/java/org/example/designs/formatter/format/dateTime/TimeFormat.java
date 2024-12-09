package org.example.designs.formatter.format.dateTime;

import org.example.designs.formatter.FormatException;
import org.example.designs.formatter.format.AbsFormat;
import org.example.designs.formatter.util.DateTimeUtil;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;

/**
 * 时间格式器
 *
 * <p>
 *     - 支持格式
 *      时分秒，时分，时分秒毫秒
 *
 *     - 支持类型
 *      - Integer
 *      - Long
 *      - String
 *      - LocalTime
 *      - LocalDateTime
 *      - Instant
 *      - Duration
 *      - Timestamp
 *      - Time
 *
 *     - 约束条件
 *      - 字符串输出格式
 *      - 时分秒
 * </p>
 *
 * @author HaiYu
 * @version 1.0.0
 * @date 2024-12-05
 */
public class TimeFormat extends AbsFormat<TimeFormat, LocalTime>{
    private String strFormat = "HH:mm:ss";
    private Integer hour = null;
    private Integer minute = null;
    private Integer second = null;

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * 修改年月日时分秒
     *
     * @param localTime
     * @return {@link LocalDateTime }
     * @throws FormatException 格式化异常
     */
    private LocalTime syncTime(LocalTime localTime) throws FormatException {
        try {
            if(null != hour) localTime = localTime.withHour(hour);
            if(null != minute) localTime = localTime.withMinute(minute);
            if(null != second) localTime = localTime.withSecond(second);
        } catch (Exception e) {
            throw new FormatException("年月日时分秒设置错误",e);
        }
        return localTime;
    }

    private TimeFormat(){}

    public static TimeFormat build(){
        return new TimeFormat()
                .min(LocalTime.MIN)
                .max(LocalTime.MAX)
                .defaultValue(LocalTime.now());
    }

    @Override
    public String toStr(Object data) throws FormatException {
        String ret = null;
        LocalTime localTime = format(data);
        localTime = syncTime(localTime);
        try {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(strFormat);
            ret = localTime.format(dateFormatter);
        } catch (Exception e) {
            throw new FormatException(String.format("时间格式[%s]无效",strFormat),e);
        }
        return ret;
    }

    @Override
    public LocalTime format(Object data) throws FormatException {
        LocalTime ret = this.defaultValue;

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

        //同步时分秒
        ret = syncTime(ret);

        if(ret.isBefore(min))
            throw new FormatException(String.format("[%s]小于最小值[%s]",ret,min));
        if(ret.isAfter(max))
            throw new FormatException(String.format("[%s]大于最大值[%s]",ret,max));
        return ret;
    }

    private LocalTime formatInt(Integer data) throws FormatException {
        return formatString(data.toString());
    }

    private LocalTime formatString(String str) throws FormatException {

        try {
            if(DateTimeUtil.isTime(str) || DateTimeUtil.isTimeSeparator(str)){
                return formatLocalTime(DateTimeUtil.toLocalTime(str));
            }

            if(DateTimeUtil.isDateTime(str) || DateTimeUtil.isDateTimeSeparator(str)){
                return formatLocalDateTime(DateTimeUtil.toLocalDateTime(str));
            }
        } catch (Exception e) {
            throw new FormatException(String.format("[%s]不是有效的时间字符串",str),e);
        }
        throw new FormatException(String.format("[%s]不是有效的时间字符串",str));
    }

    private LocalTime formatDuration(Duration data)  throws FormatException {
        LocalTime startTime = LocalTime.MIDNIGHT;
        return formatLocalTime(startTime.plus(data));
    }


    private LocalTime formatInstant(Instant data) throws FormatException  {
        return formatLocalTime(data.atZone(ZoneId.systemDefault()).toLocalTime());
    }

    private LocalTime formatTimestamp(Timestamp data) throws FormatException  {
        return formatInstant(data.toInstant());
    }


    private LocalTime formatLocalTime(LocalTime data) throws FormatException  {
        return data;
    }

    private LocalTime formatLong(Long data) throws FormatException {
        return formatInstant(Instant.ofEpochMilli(data));
    }


    private LocalTime formatLocalDateTime(LocalDateTime data) throws FormatException {
        return formatLocalTime(data.toLocalTime());
    }


    private LocalTime formatTime(Time time) throws FormatException {
        return formatLocalTime(time.toLocalTime());
    }

    public TimeFormat strFormat(String strFormat){
        this.strFormat = strFormat;
        return this;
    }

    public TimeFormat time(LocalTime time){
        this.hour = time.getHour();
        this.minute = time.getMinute();
        this.second = time.getSecond();
        return this;
    }

    public TimeFormat hour(Integer hour){
        this.hour = hour;
        return this;
    }
    public TimeFormat minute(Integer minute){
        this.minute = minute;
        return this;
    }
    public TimeFormat second(Integer second){
        this.second = second;
        return this;
    }
}
