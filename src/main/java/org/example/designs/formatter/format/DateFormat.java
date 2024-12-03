package org.example.designs.formatter.format;

import java.time.LocalDate;

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
public class DateFormat implements IFormat<LocalDate, LocalDate>{
    @Override
    public LocalDate format(LocalDate data) {
        return null;
    }
}
