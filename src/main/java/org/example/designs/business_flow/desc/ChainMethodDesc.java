package org.example.designs.business_flow.desc;

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
public class ChainMethodDesc {
    private Method method;
    private String desc;
    private String retCode;


    public ChainMethodDesc(Method method,String desc,String retCode) {
        this.method = method;
        this.desc = desc;
        this.retCode = retCode;
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

    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }
}
