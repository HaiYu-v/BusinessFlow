package org.example.designs.formatter;

import org.example.designs.formatter.format.dateTime.TimeFormat;
import org.example.designs.formatter.format.number.BigDecimalFormat;
import org.example.designs.formatter.format.dateTime.DateTimeFormat;
import org.example.designs.formatter.format.number.DoubleFormat;
import org.example.designs.formatter.format.number.IntegerFormat;
import org.example.designs.formatter.util.DateTimeUtil;
import org.example.designs.formatter.util.NumberUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Time;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.regex.Pattern;

/**
 * 格式器的测试类
 *
 * <p>
 *     TODO
 * </p>
 *
 * @author HaiYu
 * @version 1.0.0
 * @date 2024-12-03
 */
@SpringBootTest
public class TestFormatter {

    @Test
    void NumberUtilTest(){
        System.out.println(NumberUtil.IntegerRound(153, 12, RoundingMode.HALF_UP));
        System.out.println(Double.MAX_VALUE);
    }

    @Test
    void intTest(){
        try {
            //double
            System.out.println("===================double==========================");
            System.out.println(IntegerFormat.build().format(123L));
            System.out.println(IntegerFormat.build().format(13.5F));
            System.out.println(IntegerFormat.build().format(2147483646.523424));
            System.out.println(IntegerFormat.build().format(new BigDecimal(123.2332535235235252352352)));
            //string
            System.out.println("===================string==========================");
            System.out.println(IntegerFormat.build().format("2147483646.523424"));
            System.out.println(IntegerFormat.build().digit(5).format("23424.234"));
            System.out.println(IntegerFormat.build().toNegative().format("117%"));
            System.out.println(IntegerFormat.build().isNatural().format("242.234"));
            System.out.println(IntegerFormat.build().isNatural().scale(4).format("242.234"));
            //integer
            System.out.println("===================integer==========================");
            System.out.println(IntegerFormat.build().format(null));
            System.out.println(IntegerFormat.build().scale(2).digit(10).format(123));
            System.out.println(IntegerFormat.build().toPositive().format(-123));


        } catch (FormatException e) {
            e.printStackTrace();
        }
    }

    @Test
    void test() throws FormatException {

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
    @Test
    void doubleTest(){
        try {
            //double
            System.out.println("===================double==========================");


            System.out.println(new BigDecimal(0.123456789123456789).add(new BigDecimal(123456789)));
            System.out.println(BigDecimalFormat.build().scale(10,8).isCeil().format(new BigDecimal(123456789.1234567)));
//            System.out.println(BigDecimalFormat.build().scale(3,2).format(123.244));
            System.out.println(BigDecimalFormat.build().toPercentScale(12).toPercent().toStr(123456789.2432424242342342342342412312344));

            //整数和小数位
            System.out.println(DoubleFormat.build().scale(2).digit(3).format(123.244));
            //三种约小数的方式
            System.out.println(DoubleFormat.build().scale(2).format(123.245));
            System.out.println(DoubleFormat.build().isRound(2).format(123.244));
            System.out.println(DoubleFormat.build().isRound(2).format(123.245));
            System.out.println(DoubleFormat.build().isCeil(2).format(123.245));
            //字符串
            System.out.println("===================string==========================");
            //整数和小数位
            System.out.println(DoubleFormat.build().scale(2).digit(3).format("123.244"));
            //三种约小数的方式
            System.out.println(DoubleFormat.build().scale(2).format("123.245"));
            System.out.println(DoubleFormat.build().isRound(2).format("123.244"));
            System.out.println(DoubleFormat.build().isRound(2).format("123.245"));
            System.out.println(DoubleFormat.build().isCeil(2).format("123.245"));
            System.out.println(DoubleFormat.build().isCeil(2).format("24.5%"));

        } catch (FormatException e) {
            e.printStackTrace();
        }
    }

    @Test
    void bigDecimalTest(){
        try {
            //double
            System.out.println("===================double==========================");
            //整数和小数位
            System.out.println(BigDecimalFormat.build().scale(3,2).format(123.244));
            System.out.println(BigDecimalFormat.build().scale(2).digit(3).format(123.244));
            //三种约小数的方式
            System.out.println(BigDecimalFormat.build().scale(2).format(123.245));
            System.out.println(BigDecimalFormat.build().isRound(2).format(123.244));
            System.out.println(BigDecimalFormat.build().isRound(2).format(123.245));
            System.out.println(BigDecimalFormat.build().isCeil(2).format(123.245));
            //字符串
            System.out.println("===================string==========================");
            //整数和小数位
            System.out.println(BigDecimalFormat.build().scale(2).digit(3).format("123.244"));
            //三种约小数的方式
            System.out.println(BigDecimalFormat.build().scale(2).format("123.245"));
            System.out.println(BigDecimalFormat.build().isRound(2).format("123.244"));
            System.out.println(BigDecimalFormat.build().isRound(2).format("123.245"));
            System.out.println(BigDecimalFormat.build().isCeil(2).format("123.245"));
            System.out.println(BigDecimalFormat.build().isCeil(2).format("24.5%"));

        } catch (FormatException e) {
            e.printStackTrace();
        }
    }

    @Test
    void DateTimeTest(){
        try {
            //date
            System.out.println("===================date==========================");
            System.out.println(DateTimeFormat.build().format(LocalDate.now()));
            System.out.println(DateTimeFormat.build().format(LocalDateTime.now()));
            //string
            System.out.println("===================string==========================");
            System.out.println(DateTimeFormat.build().format("2024-12-03"));
            System.out.println(DateTimeFormat.build().format("2024-12-03 12:12:12"));
        }catch (FormatException e){
            e.printStackTrace();
        }
    }

    @Test
    void TimeTest(){
        try {
            //date
            System.out.println("===================date==========================");
            System.out.println(TimeFormat.build().toStr(1L));
            System.out.println(TimeFormat.build().toStr(System.currentTimeMillis()));
            System.out.println(TimeFormat.build().toStr(Instant.now()));
            System.out.println(TimeFormat.build().toStr(LocalDateTime.now()));
            System.out.println(TimeFormat.build().toStr(new Time(System.currentTimeMillis())));
            System.out.println(TimeFormat.build().toStr(Duration.ofHours(1).plusMinutes(30)));
            System.out.println(TimeFormat.build().strFormat("hh:mm:ss").toStr(LocalDateTime.now()));
            //string
            System.out.println("===================string==========================");
            System.out.println(TimeFormat.build().toStr("12:34"));
            System.out.println(TimeFormat.build().toStr("12:34:56"));
            System.out.println(TimeFormat.build().toStr("12:34:56.789"));
            System.out.println(TimeFormat.build().toStr("1234"));
            System.out.println(TimeFormat.build().toStr("123456"));
            System.out.println(TimeFormat.build().toStr("123456789"));
//            System.out.println();
//            System.out.println(TimeFormat.build().toStr("20240229"));
//            System.out.println(TimeFormat.build().toStr("2024-02-29"));
            System.out.println();
            System.out.println(TimeFormat.build().toStr("202402291234"));
            System.out.println(TimeFormat.build().toStr("20240229123456"));
            System.out.println(TimeFormat.build().toStr("20240229123456789"));
            System.out.println(TimeFormat.build().toStr("20240229T1234"));
            System.out.println(TimeFormat.build().toStr("20240229T123456"));
            System.out.println(TimeFormat.build().toStr("20240229T123456789"));
            System.out.println(TimeFormat.build().toStr("20240229 1234"));
            System.out.println(TimeFormat.build().toStr("20240229 123456"));
            System.out.println(TimeFormat.build().toStr("20240229 123456789"));
            System.out.println(TimeFormat.build().toStr("2024-02-29 12:34"));
            System.out.println(TimeFormat.build().toStr("2024-02-29 12:34:56"));
            System.out.println(TimeFormat.build().toStr("2024-02-29 12:34:56.789"));
            System.out.println(TimeFormat.build().toStr("2024-02-29T12:34"));
            System.out.println(TimeFormat.build().toStr("2024-02-29T12:34:56"));
            System.out.println(TimeFormat.build().toStr("2024-02-29T12:34:56.789"));
            System.out.println(TimeFormat.build().toStr("02291234"));
            System.out.println(TimeFormat.build().toStr("0229123456"));
            System.out.println(TimeFormat.build().toStr("02-29 12:34"));
            System.out.println(TimeFormat.build().toStr("02-29 12:34:56"));

        }catch (FormatException e){
            e.printStackTrace();
        }
    }


}
