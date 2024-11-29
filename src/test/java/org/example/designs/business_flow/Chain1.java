package org.example.designs.business_flow;

import org.example.designs.business_flow.annotation.Chain;
import org.example.designs.business_flow.annotation.Source;
import org.example.designs.business_flow.cache.GlobalValueCache;
import org.example.designs.business_flow.cache.TemporaryValueCache;
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

    @Chain(desc = "提供test1",retCode = "test1")
    public  Test1 start(){
        return new Test1(1,"xiaoming",1.0);
    }

    @Chain(desc = "接收test1",retCode = "test2")
    public Test1 conver(Test1 test1) throws InterruptedException {
        return test1;
    }

    @Chain(desc = "转换为test2",retCode = "test2",code = "converTest2")
    public Test2 conver(@Source Test2 test2) throws InterruptedException {
        return test2;
    }

    @Chain(desc = "生成一个数字",retCode = "x")
    public int start1() throws InterruptedException {
        int x = 1/0;
        return 111;
    }

    @Chain(desc = "转换为字符串",retCode = "test2",code = "str")
    public String conver1(@Source int x) throws InterruptedException {
        return Integer.toString(x);
    }

    @Chain(desc = "性能测试",retCode = "test3")
    public void performance(TemporaryValueCache temporary, GlobalValueCache global) throws InterruptedException {
//        Thread.sleep(1000);
//        System.out.println(temporary);
//        System.out.println(global);
//        System.out.println(global.get("test2"));
//        System.out.println("1"+"2"+"3"+"4"+ UUID.randomUUID());
    }

    public Object start(Object o) {
        return null;
    }


}
