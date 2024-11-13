package org.example.designs;

import org.example.aop.Function;
import org.example.designs.utils.beanUtils.BeanCopyUtil;
import org.springframework.stereotype.Component;

/**
 * @Program: 11.designs
 * @Author: 海里的鱼
 * @Create: 2024-11-07 20:46
 * @Name: TODO
 * @Desc: TODO
 */
@Component
public class CopyTest {
    private String field1;
    private String field2;

    public CopyTest(String field1, String field2) {
        this.field1 = field1;
        this.field2 = field2;
    }
    @Function
    public  void func(){
        CopyTest copyTest = new CopyTest();

        BeanCopyUtil.copyProperties(new CopyTest("2","2"),copyTest);

    }
    public CopyTest() {

    }

    public String getField1() {
        return field1;
    }

    public void setField1(String field1) {
        this.field1 = field1;
    }

    public String getField2() {
        return field2;
    }

    public void setField2(String field2) {
        this.field2 = field2;
    }
}
