package org.example.designs;

import lombok.extern.slf4j.Slf4j;
import org.example.aop.Function;
import org.example.designs.task.strategy.after.fail.RetryFail;
import org.example.designs.task.strategy.after.fail.UnThrow;
import org.example.designs.utils.beanUtils.BeanCopyUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
class ApplicationTests {

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
    @Function
    void test() {
        CopyTest copyTest = new CopyTest();

        BeanCopyUtil.copyProperties(new CopyTest("2","2"),copyTest);
        System.out.println(copyTest.getField1());
    }

}
