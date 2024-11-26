package org.example.designs.business_flow.desc;

import org.example.designs.task.TaskInfo;

import java.lang.reflect.Parameter;


/**
 * 业务点执行信息
 *
 * <p>
 *     使用组合模式, 包含了TaskInfo
 *     TaskInfo是我自己写的任务类执行信息, 本质上就是[业务点执行的基本信息]
 * </p>
 *
 * @author HaiYu
 * @version 1.0.0
 * @date 2024-11-26
 */
public class ChainInfo extends TaskInfo{
    //参数列表
    public Parameter[] parameters;
    //返回类型
    public String ret;
    //这种神经写法真的很好笑,这个变量没有卵用,单纯就是用来作个分隔符
    private String ____________________________________________ = "";

    public ChainInfo(Parameter[] parameters, Object ret,TaskInfo info) {
        super(info);
        this.parameters = parameters;
        this.ret = null == ret ? "null" : ret.getClass().getName();
    }


    public Parameter[] getParameters() {
        return parameters;
    }

    public void setParameters(Parameter[] parameters) {
        this.parameters = parameters;
    }

    public String getRet() {
        return ret;
    }

    public void setRet(String ret) {
        this.ret = ret;
    }

    public String get____________________________________________() {
        return ____________________________________________;
    }
}
