package org.example.designs;


import org.example.designs.business_flow.Chain1;
import org.example.designs.business_flow.core.BusinessFlow;
import org.example.designs.business_flow.core.BusinessFlowException;
import org.example.designs.business_flow.mapper.DataDesc;
import org.example.designs.conver.DataSource;
import org.example.designs.conver.Test1;
import org.example.designs.conver.Test2;
import org.example.designs.conver.core.ConverException;
import org.example.designs.conver.core.Converter;
import org.example.designs.conver.core.DataRuleMap;
import org.example.designs.task.TaskInfo;
import org.example.designs.task.strategy.after.fail.UnThrow;
import org.example.designs.utils.MyReflectUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Field;
import java.util.List;


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
    void test2() throws ConverException {
        DataRuleMap beanRuleMap = new DataRuleMap();
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
        System.out.println(DataDesc.class.getCanonicalName());
        System.out.println(beanRuleMap);

    }

    @Test
    void test3(){
        try {
            BusinessFlow businessFlow = BusinessFlow.build();
            Test1 end = businessFlow
                    .start()
                    .add(Chain1.class, "start")
                    .add(Chain1.class, "conver1")
                    .end(Test1.class);

//            System.out.println(end.getId());

            for (TaskInfo taskInfo : businessFlow.getInfoList()) {
                System.out.println();
                System.out.println(taskInfo.toJsonString());
                System.out.println();
            }
        } catch (BusinessFlowException e) {
            throw new RuntimeException(e);
        }
    }

}
