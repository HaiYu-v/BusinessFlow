package org.example.designs.formatter.format.dateTime;

import org.example.designs.formatter.FormatException;
import org.example.designs.formatter.format.AbsFormat;

import java.time.LocalDate;

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
    private String strFormat = "HH:mm:ss";
    @Override
    public LocalDate format(Object data) throws FormatException {
        return null;
    }

    @Override
    public String toStr(Object data) throws FormatException {
        return "";
    }
}
