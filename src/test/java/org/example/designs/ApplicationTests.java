package org.example.designs;


import cn.hutool.json.JSONUtil;
import org.example.designs.business_flow.Chain1;
import org.example.designs.business_flow.core.BusinessFlow;
import org.example.designs.business_flow.core.BusinessFlowException;
import org.example.designs.conver.DataSource;
import org.example.designs.conver.Test1;
import org.example.designs.conver.Test2;
import org.example.designs.conver.core.ConverException;
import org.example.designs.conver.core.Converter;
import org.example.designs.conver.core.DataRules;
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


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * 测试规则转换
     *
     * @throws ConverException 转换异常
     */
    @Test
    void test2() throws ConverException {
        DataRules beanRuleMap = new DataRules();
        String rule = "{\n" +
                "    \"targetCode\":\"test2\",\n" +
                "    \"rules\":{\n" +
                "        \"id\":{\"type\":\"1\",\"formula\":\"123\"},\n" +
                "        \"name1\":{\"type\":\"1\",\"formula\":\"\"price\"\"},\n" +
                "        \"price\":{\"type\":\"0\",\"source\":[\"price\"]},\n" +
                "    }\n" +
                "}";
        Converter.analysis(rule,beanRuleMap);
        DataSource dataSource = new DataSource();

        Test1 test1 = new Test1(1, "test1", 2.0);
        Test2 test2 = new Test2();
        dataSource.put("test1",test1);
        dataSource.put("price",3);
        try {
            Converter.conver(test2,"test2",beanRuleMap,dataSource);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        System.out.println(JSONUtil.toJsonPrettyStr(test2));
//        System.out.println(DataDesc.class.getCanonicalName());
//        System.out.println(beanRuleMap);

    }

    @Test
    void test3(){
        try {
            BusinessFlow businessFlow = BusinessFlow.build("测试业务流性能");
            for(int i=0 ;i<1; i++){
                businessFlow.add(Chain1.class,"performance");
            }
            businessFlow.getGlobalValueCache().put("test2",new Test1(1,"xiaoming",1.0));
            businessFlow
//                    .add(Chain1.class, "start")
//                    .add(Chain1.class, "conver")
//                    .add(Chain1.class, "converTest2")
                    .start();
//            System.out.println(end.getName());

            StringBuilder stringBuilder = new StringBuilder();
            System.out.println(businessFlow.getInfoJSON());
            System.out.println(businessFlow.getVisualJSON());

        } catch (BusinessFlowException e) {
            throw new RuntimeException(e);
        }
    }


    @Test
    void test4(){
        TestController controller = new TestController();
        System.out.println(controller.test());
    }


    private String getMethodName(MyFunction myFunction){
        return myFunction.getImplMethodName();
    }

    private String getClass(MyFunction myFunction){
        return myFunction.getImplClass();
    }

}
