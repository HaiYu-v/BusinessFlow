package org.example.designs.business_flow;

import lombok.extern.slf4j.Slf4j;
import org.example.designs.business_flow.cache.GlobalCache;
import org.example.designs.business_flow.cache.TemporaryCache;
import org.example.designs.business_flow.core.BusinessFlow;
import org.example.designs.business_flow.core.BusinessFlowException;
import org.example.designs.business_flow.core.IChain;
import org.example.designs.conver.Test1;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@Slf4j
@SpringBootTest
public class BusinessTest {

    private static final Logger logger = LoggerFactory.getLogger(BusinessTest.class);

    @Test
    void test1(){
        try {
            BusinessFlow businessFlow = BusinessFlow.build("测试业务流性能");
//            for(int i=0 ;i<100; i++){
//                businessFlow.add(Chain1.class,"performance");
//            }

            businessFlow.getGlobalValueCache().put("test2",new Test1(1,"xiaoming",1.0));
            businessFlow
                    .add(Chain1.class, "Chain1::start")
                    .add(Chain1.class, "new Chain1().conver()")
                    .add(Chain1.class, "converTest2")
                    .end();
//            System.out.println(end.getName());

//            StringBuilder stringBuilder = new StringBuilder();
//            System.out.println(businessFlow.getInfoJSON());
//            System.out.println(businessFlow.getVisualLog());
//            log.info(businessFlow.getVisualLog());
        } catch (BusinessFlowException e) {
            e.printStackTrace();
        }
    }

    @Test
    void test2(){
        TestController controller = new TestController();
        System.out.println(controller.test());
    }

    @Test
    void test3() {


        BusinessFlow businessFlow = null;
        try {
            businessFlow = BusinessFlow
                    .build("基本类型转换测试")
                    .add("匿名类测试", "ret", new IChain() {
                        @Override
                        public Object method(TemporaryCache temporaryCache, GlobalCache globalCache) {
                            return "匿名类执行";
                        }
                    });
//                    .add(Chain1.class, "start1")
//                    .add(Chain1.class, "conver1")

        } catch (BusinessFlowException e) {
            e.printStackTrace();
        } finally {
            System.out.println(businessFlow.getVisualLog());
        }
    }
}
