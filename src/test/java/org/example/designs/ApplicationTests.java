package org.example.designs;


import lombok.extern.slf4j.Slf4j;
import org.example.designs.aop.Function;
import org.example.designs.task.strategy.after.fail.UnThrow;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


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


}
