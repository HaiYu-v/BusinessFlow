package org.example.designs.formatter;

import org.example.designs.formatter.format.dateTime.DateFormat;
import org.example.designs.formatter.format.dateTime.DateTimeFormat;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Time;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;

/**
 * 日期格式器的测试类
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
public class DateTimeTest {

    @Test
    void TimeTest(){
        try {
            //date
            System.out.println("===================date==========================");
            System.out.println(DateTimeFormat.build().toStr(1L));
            System.out.println(DateTimeFormat.build().toStr(System.currentTimeMillis()));
            System.out.println(DateTimeFormat.build().toStr(Instant.now()));
            System.out.println(DateTimeFormat.build().toStr(LocalDateTime.now()));
            System.out.println(DateTimeFormat.build().toStr(new Time(System.currentTimeMillis())));
            System.out.println(DateTimeFormat.build().toStr(Duration.ofHours(1).plusMinutes(30)));
            System.out.println(DateTimeFormat.build().strFormat("hh:mm:ss").toStr(LocalDateTime.now()));
            //string
            System.out.println("===================string==========================");
            System.out.println(DateTimeFormat.build().toStr("12:34"));
            System.out.println(DateTimeFormat.build().toStr("12:34:56"));
            System.out.println(DateTimeFormat.build().toStr("12:34:56.789"));
            System.out.println(DateTimeFormat.build().toStr("1234"));
            System.out.println(DateTimeFormat.build().toStr("123456"));
            System.out.println(DateTimeFormat.build().toStr("123456789"));
            System.out.println();
            System.out.println(DateTimeFormat.build().toStr("202402291234"));
            System.out.println(DateTimeFormat.build().toStr("20240229123456"));
            System.out.println(DateTimeFormat.build().toStr("20240229123456789"));
            System.out.println(DateTimeFormat.build().toStr("20240229T1234"));
            System.out.println(DateTimeFormat.build().toStr("20240229T123456"));
            System.out.println(DateTimeFormat.build().toStr("20240229T123456789"));
            System.out.println(DateTimeFormat.build().toStr("20240229 1234"));
            System.out.println(DateTimeFormat.build().toStr("20240229 123456"));
            System.out.println(DateTimeFormat.build().toStr("20240229 123456789"));
            System.out.println(DateTimeFormat.build().toStr("2024-02-29 12:34"));
            System.out.println(DateTimeFormat.build().toStr("2024-02-29 12:34:56"));
            System.out.println(DateTimeFormat.build().toStr("2024-02-29 12:34:56.789"));
            System.out.println(DateTimeFormat.build().toStr("2024-02-29T12:34"));
            System.out.println(DateTimeFormat.build().toStr("2024-02-29T12:34:56"));
            System.out.println(DateTimeFormat.build().toStr("2024-02-29T12:34:56.789"));
            System.out.println(DateTimeFormat.build().toStr("02291234"));
            System.out.println(DateTimeFormat.build().toStr("0229123456"));
            System.out.println(DateTimeFormat.build().toStr("02-29 12:34"));
            System.out.println(DateTimeFormat.build().toStr("02-29 12:34:56"));

        }catch (FormatException e){
            e.printStackTrace();
        }
    }

    @Test
    void DateTest(){
        try {
            //date
            System.out.println("===================date==========================");
            System.out.println(DateFormat.build().toStr(1L));
            System.out.println(DateFormat.build().toStr(System.currentTimeMillis()));
            System.out.println(DateFormat.build().toStr(Instant.now()));
            System.out.println(DateFormat.build().toStr(LocalDateTime.now()));
            System.out.println(DateFormat.build().strFormat("yyyy-MM-dd").toStr(LocalDateTime.now()));
            //string
            System.out.println("===================string==========================");
            System.out.println(DateFormat.build().toStr("20240229"));
            System.out.println(DateFormat.build().toStr("0229"));
            System.out.println(DateFormat.build().toStr("02-29"));
            System.out.println(DateFormat.build().toStr("2024-02-29"));
            System.out.println();
            System.out.println(DateFormat.build().toStr("202402291234"));
            System.out.println(DateFormat.build().toStr("20240229123456"));
            System.out.println(DateFormat.build().toStr("20240229123456789"));
            System.out.println(DateFormat.build().toStr("20240229T1234"));
            System.out.println(DateFormat.build().toStr("20240229T123456"));
            System.out.println(DateFormat.build().toStr("20240229T123456789"));
            System.out.println(DateFormat.build().toStr("20240229 1234"));
            System.out.println(DateFormat.build().toStr("20240229 123456"));
            System.out.println(DateFormat.build().toStr("20240229 123456789"));
            System.out.println(DateFormat.build().toStr("2024-02-29 12:34"));
            System.out.println(DateFormat.build().toStr("2024-02-29 12:34:56"));
            System.out.println(DateFormat.build().toStr("2024-02-29 12:34:56.789"));
            System.out.println(DateFormat.build().toStr("2024-02-29T12:34"));
            System.out.println(DateFormat.build().toStr("2024-02-29T12:34:56"));
            System.out.println(DateFormat.build().toStr("2024-02-29T12:34:56.789"));
            System.out.println(DateFormat.build().toStr("02291234"));
            System.out.println(DateFormat.build().toStr("0229123456"));
            System.out.println(DateFormat.build().toStr("02-29 12:34"));
            System.out.println(DateFormat.build().toStr("02-29 12:34:56"));

        }catch (FormatException e){
            e.printStackTrace();
        }
    }

    @Test
    void DateTimeFormatTest(){
        try {
            //date
            System.out.println("===================date==========================");
            System.out.println(DateTimeFormat.build().toStr(1L));
            System.out.println(DateTimeFormat.build().toStr(System.currentTimeMillis()));
            System.out.println(DateTimeFormat.build().toStr(Instant.now()));
            System.out.println(DateTimeFormat.build().toStr(LocalDateTime.now()));
            System.out.println(DateTimeFormat.build().toStr(new Time(System.currentTimeMillis())));
            System.out.println(DateTimeFormat.build().strFormat("yyyy-MM-dd hh:mm:ss").toStr(LocalDateTime.now()));
            //string
            System.out.println("===================string==========================");
            System.out.println(DateTimeFormat.build().toStr("12:34"));
            System.out.println(DateTimeFormat.build().toStr("12:34:56"));
            System.out.println(DateTimeFormat.build().toStr("12:34:56.789"));
            System.out.println(DateTimeFormat.build().toStr("1234"));
            System.out.println(DateTimeFormat.build().toStr("123456"));
            System.out.println(DateTimeFormat.build().toStr("123456789"));
            System.out.println();
            System.out.println(DateTimeFormat.build().toStr("20240229"));
            System.out.println(DateTimeFormat.build().toStr("0229"));
            System.out.println(DateTimeFormat.build().toStr("02-29"));
            System.out.println(DateTimeFormat.build().toStr("2024-02-29"));
            System.out.println();
            System.out.println(DateTimeFormat.build().toStr("202402291234"));
            System.out.println(DateTimeFormat.build().toStr("20240229123456"));
            System.out.println(DateTimeFormat.build().toStr("20240229123456789"));
            System.out.println(DateTimeFormat.build().toStr("20240229T1234"));
            System.out.println(DateTimeFormat.build().toStr("20240229T123456"));
            System.out.println(DateTimeFormat.build().toStr("20240229T123456789"));
            System.out.println(DateTimeFormat.build().toStr("20240229 1234"));
            System.out.println(DateTimeFormat.build().toStr("20240229 123456"));
            System.out.println(DateTimeFormat.build().toStr("20240229 123456789"));
            System.out.println(DateTimeFormat.build().toStr("2024-02-29 12:34"));
            System.out.println(DateTimeFormat.build().toStr("2024-02-29 12:34:56"));
            System.out.println(DateTimeFormat.build().toStr("2024-02-29 12:34:56.789"));
            System.out.println(DateTimeFormat.build().toStr("2024-02-29T12:34"));
            System.out.println(DateTimeFormat.build().toStr("2024-02-29T12:34:56"));
            System.out.println(DateTimeFormat.build().toStr("2024-02-29T12:34:56.789"));
            System.out.println(DateTimeFormat.build().toStr("02291234"));
            System.out.println(DateTimeFormat.build().toStr("0229123456"));
            System.out.println(DateTimeFormat.build().toStr("02-29 12:34"));
            System.out.println(DateTimeFormat.build().toStr("02-29 12:34:56"));
        }catch (FormatException e){
            e.printStackTrace();
        }
    }
}
