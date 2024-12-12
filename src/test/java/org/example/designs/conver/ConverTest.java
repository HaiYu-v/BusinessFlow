package org.example.designs.conver;

import cn.hutool.json.JSONUtil;
import org.example.designs.conver.core.ConverException;
import org.example.designs.conver.core.Converter;
import org.example.designs.conver.core.DataRules;
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
 * @date 2024-12-12
 */
@SpringBootTest
public class ConverTest {

    @Test
    void test1() throws ConverException {
        DataRules beanRuleMap = new DataRules();
        String rule = "{\n" +
                "    \"sourceCode\":\"test1\",\n" +
                "    \"rules\":{\n" +
                "        \"id\":{\"type\":\"1\",\"formula\":\"123\"},\n" +
                "        \"name1\":{\"type\":\"1\",\"formula\":\"#price + 1\"},\n" +
                "        \"price\":{\"type\":\"0\",\"source\":\"[test1.price]\"}\n" +
                "    }\n" +
                "}";
        Converter.analysis(rule,beanRuleMap);
        DataSource dataSource = new DataSource();
        dataSource.put("price",3);
        Test1 test1 = new Test1(1, "test1", 2.0);
        dataSource.put("test1",test1);
        Test2 test2 = null;
        try {
            test2 = Converter.conver(Test2.class,"test1",beanRuleMap,dataSource,true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(JSONUtil.toJsonPrettyStr(test2));
//        System.out.println(DataDesc.class.getCanonicalName());
//        System.out.println(beanRuleMap);

    }
}
