package org.example.designs.formatter;

import org.example.designs.formatter.format.IntegerFormat;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

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
    void intTest(){
        System.out.println(IntegerFormat.build().format(null));
        System.out.println(IntegerFormat.build().format(123));
        System.out.println(IntegerFormat.build().format(123L));
        System.out.println(IntegerFormat.build().format(234.5F));
        System.out.println(IntegerFormat.build().format(35.523424));
        System.out.println(IntegerFormat.build().format(new BigDecimal(123.2332535235235252352352)));
        System.out.println(IntegerFormat.build().format("23424"));
        System.out.println(IntegerFormat.build().format("23424.234"));
        System.out.println(IntegerFormat.build().format("117%"));
    }

    @Test
    void doubleTest(){

    }
}
