package org.example.designs.business_flow;

import org.example.designs.business_flow.annotation.Chain;
import org.example.designs.conver.Test1;
import org.example.designs.conver.Test2;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * TODO
 *
 * <p>
 *     TODO
 * </p>
 *
 * @author HaiYu
 * @version 1.0.0
 * @date 2024-11-22
 */
@Component
public class Chain1 {

    @Chain(desc = "起始",retCode = "test1")
    public  Test1 start(){
        return new Test1();
    }

    @Chain(desc = "转换为test1",retCode = "test1",code = "conver1")
    public Test1 conver(Test1 test1) throws InterruptedException {
        Thread.sleep(1000);
        return test1;
    }

    @Chain(desc = "转换为test2",retCode = "test2")
    public Test2 conver(Test2 test2) throws InterruptedException {
        return test2;
    }

    @Chain(desc = "性能测试",retCode = "test2")
    public void performance() throws InterruptedException {
        Thread.sleep(1);
        System.out.println("1"+"2"+"3"+"4"+ UUID.randomUUID());
    }

    public Object start(Object o) {
        return null;
    }


}
