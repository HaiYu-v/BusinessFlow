package org.example.designs.formatter.format.dateTime;

import org.example.designs.formatter.FormatException;
import org.example.designs.formatter.format.AbsFormat;
import org.example.designs.formatter.format.IFormat;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.*;
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
    private String strFormat = "HH:mm:ss";


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
        }else if(data instanceof Timestamp){
            ret = formatTimestamp((Timestamp) data);
        }else if(data instanceof Instant){
            ret = formatInstant((Instant) data);
        }else if(data instanceof Duration){
            ret = formatDuration((Duration) data);
        }
        return ret;
    }

    private LocalTime formatDuration(Duration data) {
        LocalTime startTime = LocalTime.MIDNIGHT;
        return formatLocalTime(startTime.plus(data));
    }


    private LocalTime formatInstant(Instant data) {
        return formatLocalTime(LocalTime.from(data));
    }

    private LocalTime formatTimestamp(Timestamp data) {
        return formatInstant(data.toInstant());
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
