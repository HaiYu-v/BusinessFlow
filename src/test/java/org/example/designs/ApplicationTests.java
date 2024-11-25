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
import org.example.designs.conver.core.DataRules;
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
        DataRules beanRuleMap = new DataRules();
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
            Long startTime = System.currentTimeMillis();
            BusinessFlow businessFlow = BusinessFlow.build();
            for(int i=0 ;i<10000; i++){
                businessFlow.add(Chain1.class,"performance");
            }
//            Chain1 chain1 = new Chain1();
//            Test1 end = businessFlow
//                    .add(Chain1.class, "conver1")
//                    .start(Test1.class);
//            System.out.println(end.getName());

            businessFlow.start();

            System.out.println("总耗时："+(System.currentTimeMillis()-startTime));

//            for (TaskInfo taskInfo : businessFlow.getInfoList()) {
//                System.out.println();
//                System.out.println(taskInfo.toJsonString());
//                System.out.println();
//            }
        } catch (BusinessFlowException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void test4(){
//        System.out.println(getMethodName(Chain1::conver));
//        System.out.println(getClass(chain1::start));
    }

    private String getMethodName(MyFunction myFunction){
        return myFunction.getImplMethodName();
    }

    private String getClass(MyFunction myFunction){
        return myFunction.getImplClass();
    }

}
