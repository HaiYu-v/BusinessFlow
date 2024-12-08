package org.example.designs.formatter;

import org.example.designs.formatter.util.DateTimeUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * TODO
 *
 * <p>
 *     TODO
 * </p>
 *
 * @author HaiYu
 * @version 1.0.0
 * @date 2024-12-08
 */
@SpringBootTest
public class UtilTest {
    @Test
    void TimeUtilTest(){
        System.out.println(DateTimeUtil.toLocalTime("12:34"));
        System.out.println(DateTimeUtil.toLocalTime("12:34:56"));
        System.out.println(DateTimeUtil.toLocalTime("12:34:56.789"));
        System.out.println(DateTimeUtil.toLocalTime("1234"));
        System.out.println(DateTimeUtil.toLocalTime("123456"));
        System.out.println(DateTimeUtil.toLocalTime("123456789"));
        System.out.println();
        System.out.println(DateTimeUtil.toLocalDate("20240229"));
        System.out.println(DateTimeUtil.toLocalDate("0228"));
        System.out.println(DateTimeUtil.toLocalDate("02-29"));
        System.out.println(DateTimeUtil.toLocalDate("2024-02-29"));
        System.out.println();
        System.out.println(DateTimeUtil.toLocalDateTime("202402291234"));
        System.out.println(DateTimeUtil.toLocalDateTime("20240229123456"));
        System.out.println(DateTimeUtil.toLocalDateTime("20240229123456789"));
        System.out.println(DateTimeUtil.toLocalDateTime("20240229T1234"));
        System.out.println(DateTimeUtil.toLocalDateTime("20240229T123456"));
        System.out.println(DateTimeUtil.toLocalDateTime("20240229T123456789"));
        System.out.println(DateTimeUtil.toLocalDateTime("20240229 1234"));
        System.out.println(DateTimeUtil.toLocalDateTime("20240229 123456"));
        System.out.println(DateTimeUtil.toLocalDateTime("20240229 123456789"));
        System.out.println(DateTimeUtil.toLocalDateTime("2024-02-29 12:34"));
        System.out.println(DateTimeUtil.toLocalDateTime("2024-02-29 12:34:56"));
        System.out.println(DateTimeUtil.toLocalDateTime("2024-02-29 12:34:56.789"));
        System.out.println(DateTimeUtil.toLocalDateTime("2024-02-29T12:34"));
        System.out.println(DateTimeUtil.toLocalDateTime("2024-02-29T12:34:56"));
        System.out.println(DateTimeUtil.toLocalDateTime("2024-02-29T12:34:56.789"));

        System.out.println(DateTimeUtil.toLocalDateTime("02291234"));
        System.out.println(DateTimeUtil.toLocalDateTime("0229123456"));
        System.out.println(DateTimeUtil.toLocalDateTime("02-29 12:34"));
        System.out.println(DateTimeUtil.toLocalDateTime("02-29 12:34:56"));

    }
}
