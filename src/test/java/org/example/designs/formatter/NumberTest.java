package org.example.designs.formatter;

import org.example.designs.formatter.format.number.BigDecimalFormat;
import org.example.designs.formatter.format.number.DoubleFormat;
import org.example.designs.formatter.format.number.IntegerFormat;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

/**
 * 数字格式器的测试类
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
public class NumberTest {
    @Test
    void IntegerFormatTest(){
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
    void DoubleFormatTest(){
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
    void BigDecimalFormatTest(){
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
}
