package org.example.designs.chain.desc;

import java.lang.reflect.Method;

/**
 * 业务处理方法描述
 *
 * <p>
 *     TODO
 * </p>
 * 
 * @date  2024-11-21 
 * @author  HaiYu
 * @version 1.0.0
 */
public class MethodDesc {
    private Method method;
    private String desc;

    public MethodDesc(Method method, String desc) {
        this.method = method;
        this.desc = desc;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
