package org.example.designs;


import cn.hutool.json.JSONUtil;
import org.example.designs.business_flow.Chain1;
import org.example.designs.business_flow.TestController;
import org.example.designs.business_flow.cache.GlobalCache;
import org.example.designs.business_flow.cache.TemporaryCache;
import org.example.designs.business_flow.core.BusinessFlow;
import org.example.designs.business_flow.core.BusinessFlowException;
import org.example.designs.business_flow.core.IChain;
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

    @Test
    void test3(){
        try {
            BusinessFlow businessFlow = BusinessFlow.build("测试业务流性能");
            for(int i=0 ;i<100; i++){
                businessFlow.add(Chain1.class,"performance");
            }

            businessFlow.getGlobalValueCache().put("test2",new Test1(1,"xiaoming",1.0));
            businessFlow
                    .add(Chain1.class, "start")
                    .add(Chain1.class, "conver")
                    .add(Chain1.class, "converTest2")
                    .start();
//            System.out.println(end.getName());

//            StringBuilder stringBuilder = new StringBuilder();
//            System.out.println(businessFlow.getInfoJSON());
            System.out.println(businessFlow.getVisualLog());

        } catch (BusinessFlowException e) {
            throw new RuntimeException(e);
        }
    }


    @Test
    void test4(){
        TestController controller = new TestController();
        System.out.println(controller.test());
    }
    public static void func(int x){

    }

    @Test
    void test5(){
        BusinessFlow businessFlow = BusinessFlow.build("基本类型转换测试");

        String str = null;
        try {
            str = businessFlow
                    .add("匿名类测试", "ret", new IChain() {
                        @Override
                        public Object method(TemporaryCache temporaryCache, GlobalCache globalCache) {
                            return "匿名类执行";
                        }
                    })
//                    .add(Chain1.class, "start1")
//                    .add(Chain1.class, "conver1")
                    .start(String.class);
        } catch (BusinessFlowException e) {
            e.printStackTrace();
        }
        System.out.println(businessFlow.getVisualLog());
        System.out.println(str);
    }


    private String getMethodName(MyFunction myFunction){
        return myFunction.getImplMethodName();
    }

    private String getClass(MyFunction myFunction){
        return myFunction.getImplClass();
    }

}
