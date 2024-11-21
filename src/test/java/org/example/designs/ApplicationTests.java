package org.example.designs;


import cn.hutool.core.util.ReflectUtil;
import lombok.extern.slf4j.Slf4j;
import org.example.designs.aop.Function;
import org.example.designs.chain.conver.ConverHandler;
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
        String rule = "{\n" +
                "    \"targetCode\":\"code\",\n" +
                "    \"rules\":{\n" +
                "        \"file1\":{\"type\":\"常量||映射||公式\",\"code\":\"code1\",\"field\":\"file\",\"formula\":\"=c1+c2\"},\n" +
                "        \"file2\":{\"type\":\"常量\",\"code\":\"code1\",\"field\":\"file\",\"formula\":\"=1\"},\n" +
                "        \"file3\":{\"type\":\"映射\",\"code\":\"code2\",\"field\":\"file\"},\n" +
                "        \"file4\":{\"type\":\"公式\",\"formula\":\"=c1+c2\"}\n" +
                "    }\n" +
                "}";

        try {
            ConverHandler.analysisRule(rule);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

}
