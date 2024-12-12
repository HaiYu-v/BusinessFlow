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
import org.example.designs.task.postProcessor.after.fail.UnThrow;
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







    private String getMethodName(MyFunction myFunction){
        return myFunction.getImplMethodName();
    }

    private String getClass(MyFunction myFunction){
        return myFunction.getImplClass();
    }

}
