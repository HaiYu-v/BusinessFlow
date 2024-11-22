package org.example.designs;


import lombok.extern.slf4j.Slf4j;
import org.example.designs.conver.DataSource;
import org.example.designs.conver.Test1;
import org.example.designs.conver.Test2;
import org.example.designs.conver.core.BeanRuleMap;
import org.example.designs.conver.core.Converter;
import org.example.designs.task.strategy.after.fail.UnThrow;
import org.example.designs.utils.MyReflectUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Field;
import java.util.List;


@Slf4j
@SpringBootTest
class ApplicationTests  {

    @Test
    void contextLoads() {


        PrintTask task = new PrintTask(1,10000);
//        task.setAfterExecute(new RetryFail());
        task.setAfterExecute(new UnThrow());
        try {
            task.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(task.getInfo());
    }

    @Test
    void test1(){
        List<Field> fields = MyReflectUtil.getFieldsWithGetterAndSetter(Test1.class);
        System.out.println(fields);
    }


    @Test
    void test2(){
        BeanRuleMap beanRuleMap = new BeanRuleMap();
        String rule = "{\n" +
                "    \"targetCode\":\"test2\",\n" +
                "    \"rules\":{\n" +
                "        \"id\":{\"type\":\"0\",\"code\":\"test1\",\"field\":\"id\",\"formula\":\"\"},\n" +
                "        \"name\":{\"type\":\"0\",\"code\":\"test1\",\"field\":\"name\",\"formula\":\"\"},\n" +
                "        \"price\":{\"type\":\"0\",\"code\":\"test1\",\"field\":\"price\"},\n" +
                "    }\n" +
                "}";
        Converter.analysis(rule,beanRuleMap);
        DataSource dataSource = new DataSource();

        Test1 test1 = new Test1(1, "test1", 2);
        Test2 test2 = new Test2();
        dataSource.put("test1",test1);
        dataSource.put("price",3);
        try {
            Converter.conver(test2,"test2",beanRuleMap,dataSource);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.out.println(test2);
        System.out.println(beanRuleMap);

    }

}
