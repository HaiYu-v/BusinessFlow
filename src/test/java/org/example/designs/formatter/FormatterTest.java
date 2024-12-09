package org.example.designs.formatter;

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
 * @date 2024-12-09
 */
@SpringBootTest
public class FormatterTest {
    @Test
    public void intTest() throws FormatException {
        Formatter formatter = new Formatter();
        formatter.putIntegerFormat("test1").isRound(4);
        System.out.println(formatter.format(12345, "test1").toString());
    }
}
