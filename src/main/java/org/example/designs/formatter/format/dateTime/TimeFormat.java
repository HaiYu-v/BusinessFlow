package org.example.designs.formatter.format.dateTime;

import org.example.designs.formatter.FormatException;
import org.example.designs.formatter.format.AbsFormat;
import org.example.designs.formatter.format.IFormat;

import java.sql.Time;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * 时间格式器
 *
 * <p>
 *     时，分，秒，毫秒
 * </p>
 *
 * @author HaiYu
 * @version 1.0.0
 * @date 2024-12-05
 */
public class TimeFormat extends AbsFormat<TimeFormat, LocalTime> implements IFormat<Object, LocalTime> {
    private String strFormat = "HH:mm:ss:SSS";


    private TimeFormat(){}

    public static TimeFormat build(){
        return new TimeFormat()
                .min(LocalTime.MIN)
                .max(LocalTime.MAX)
                .defaultValue(LocalTime.now());
    }

    public String toStr(Object data) throws FormatException {
        LocalTime format = format(data);
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(strFormat);
        return format.format(dateFormatter);
    }

    @Override
    public LocalTime format(Object data) throws FormatException {
        LocalTime ret = this.defaultValue;

        if(data instanceof Integer) {
            ret = formatInt((Integer) data);
        }else if(data instanceof String){
            String str = (String) data;
            if(!str.isEmpty()) ret = formatString(str);
        }else if(data instanceof LocalTime){
            ret = formatLocalTime((LocalTime) data);
        } else if(data instanceof Time){
            ret = formatTime((Time) data);
        }else if(data instanceof LocalDateTime){
            ret = formatLocalDateTime((LocalDateTime) data);
        }
        return ret;
    }



    private LocalTime formatLocalTime(LocalTime data) {
        return data;
    }

    private LocalTime formatInt(Integer data) {
        return null;
    }

    private LocalTime formatString(String str) {
        return null;
    }
    private LocalTime formatLocalDateTime(LocalDateTime data) {
        return formatLocalTime(data.toLocalTime());
    }


    private LocalTime formatTime(Time time) {
        return formatLocalTime(time.toLocalTime());
    }

    public TimeFormat strFormat(String strFormat) {
        this.strFormat = strFormat;
        return this;
    }


}
