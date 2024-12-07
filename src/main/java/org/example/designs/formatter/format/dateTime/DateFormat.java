package org.example.designs.formatter.format.dateTime;

import org.example.designs.formatter.FormatException;
import org.example.designs.formatter.format.AbsFormat;
import org.example.designs.formatter.util.DateTimeUtil;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;

/**
 * 日期格式器
 *
 * <p>
 *     1.年月日
 *     2.年月
 *     3.月日
 * </p>
 *
 * @author HaiYu
 * @version 1.0.0
 * @date 2024-12-05
 */
public class DateFormat extends AbsFormat<DateFormat, LocalDate> {
    private String strFormat = "yyyy-MM-dd";


    private DateFormat(){}

    public static DateFormat build(){
        return new DateFormat()
                .min(LocalDate.MIN)
                .max(LocalDate.MAX)
                .defaultValue(LocalDate.now());
    }

    @Override
    public String toStr(Object data) throws FormatException {
        String ret = null;
        LocalDate localDate = format(data);
        try {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(strFormat);
            ret = localDate.format(dateFormatter);
        } catch (Exception e) {
            throw new FormatException(String.format("日期格式[%s]无效",strFormat),e);
        }
        return ret;
    }

    @Override
    public LocalDate format(Object data) throws FormatException {
        LocalDate ret = this.defaultValue;

        try {
            if(data instanceof Integer){
                ret = formatInt((Integer) data);
            }else if(data instanceof Long) {
                ret = formatLong((Long) data);
            }else if(data instanceof String){
                String str = (String) data;
                if(!str.isEmpty()) ret = formatString(str);
            }else if(data instanceof LocalDate){
                ret = formatLocalDate((LocalDate) data);
            } else if(data instanceof Date){
                ret = formatDate((Date) data);
            }else if(data instanceof LocalDateTime){
                ret = formatLocalDateTime((LocalDateTime) data);
            }else if(data instanceof Timestamp){
                ret = formatTimestamp((Timestamp) data);
            }else if(data instanceof Instant){
                ret = formatInstant((Instant) data);
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

    private LocalDate formatString(String str) throws FormatException {

        try {
            if(DateTimeUtil.isDate(str) || DateTimeUtil.isDateSeparator(str)){
                return formatLocalDate(DateTimeUtil.toLocalDate(str));
            }
            
            if(DateTimeUtil.isDateTime(str) || DateTimeUtil.isDateTimeSeparator(str)){
                return formatLocalDateTime(DateTimeUtil.toLocalDateTime(str));
            }
            
        } catch (Exception e) {
            throw new FormatException(String.format("[%s]不是有效的日期字符串",str),e);
        }
        throw new FormatException(String.format("[%s]不是有效的日期字符串",str));
    }

    private LocalDate formatInt(Integer data) throws FormatException {
        return formatString(data.toString());
    }

    private LocalDate formatInstant(Instant data) throws FormatException {
        return formatLocalDate(data.atZone(ZoneId.systemDefault()).toLocalDate());
    }

    private LocalDate formatTimestamp(Timestamp data) throws FormatException {
        return formatInstant(data.toInstant());
    }


    private LocalDate formatLocalDate(LocalDate data) throws FormatException {
        return data;
    }

    private LocalDate formatLong(Long data) throws FormatException {
        return formatInstant(Instant.ofEpochMilli(data));
    }


    private LocalDate formatLocalDateTime(LocalDateTime data) throws FormatException {
        return formatLocalDate(data.toLocalDate());
    }

    private LocalDate formatDate(Date Date) throws FormatException {
        return formatLocalDate(Date.toLocalDate());
    }

    public DateFormat strFormat(String strFormat) {
        this.strFormat = strFormat;
        return this;
    }

}
