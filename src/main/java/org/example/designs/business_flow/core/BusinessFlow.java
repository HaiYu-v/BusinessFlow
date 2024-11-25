package org.example.designs.business_flow.core;

import cn.hutool.core.util.StrUtil;
import org.example.designs.business_flow.annotation.Chain;
import org.example.designs.business_flow.annotation.Source;
import org.example.designs.business_flow.cache.GlobalValueCache;
import org.example.designs.business_flow.cache.TemporaryValueCache;
import org.example.designs.business_flow.context.IContext;
import org.example.designs.business_flow.context.SpringBeanContext;
import org.example.designs.conver.core.DataRules;
import org.example.designs.conver.core.ConverException;
import org.example.designs.conver.core.Converter;
import org.example.designs.business_flow.desc.ChainDesc;
import org.example.designs.task.AbstractTask;
import org.example.designs.task.TaskException;
import org.example.designs.task.TaskInfo;

import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.function.Function;


/**
 * 业务流
 *
 * <p>
 *     1.业务流节点的处理方法必须使用 {@link Chain} 注解
 *     2.业务流节点的desc：add()传入desc > @Chain注解的desc
 * </p>
 *
 * @author HaiYu
 * @version 1.0.0
 * @date 2024-11-22
 */
public class BusinessFlow {
    //业务流的锁
    private final Object startupShutdownMonitor;
    //data容器内
    static private IContext CONTEXT = new SpringBeanContext();
    //业务点执行列表
    private Queue<ChainDesc> chainList = new LinkedList<>();
    //业务执行信息列表
    private List<TaskInfo> infoList;
    //全局数据的缓存，整条业务流程里的缓存
    private GlobalValueCache globalValueCache;
    //临时数据的缓存，只传递一次
    private TemporaryValueCache temporaryValueCache;
    //转换规则缓存
    private DataRules ruleCache;
    //当前ChainDesc
    private ChainDesc curChain;

    //构造方法私有
    private BusinessFlow() {
        this.startupShutdownMonitor = new Object();
        this.infoList = new ArrayList<>();
        this.globalValueCache = new GlobalValueCache();
        this.temporaryValueCache = new TemporaryValueCache();
        this.ruleCache = new DataRules();
        this.curChain = null;
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * 构建方法
     *
     * @return {@link BusinessFlow }
     */
    public static BusinessFlow build(){
        BusinessFlow businessFlow = new BusinessFlow();
        return businessFlow;
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * 启动方法
     *
     * @param retType 业务流返回值类型
     * @return {@link BusinessFlow }
     * @throws BusinessFlowException Data异常
     */
    public <T> T start(Class<T> retType) throws BusinessFlowException {
        try {
            synchronized (startupShutdownMonitor) {
                String LastRetCode = null;
                for(int i=0; !chainList.isEmpty(); i++){
                    ChainDesc chainDesc = chainList.poll();
                    //业务点所需参数
                    Parameter[] parameters = chainDesc.getParameters();
                    //参数赋值
                    chainDesc.setParams(importParams(parameters));
                    //获取参数后，清除临时缓存
                    temporaryValueCache.clear();
                    //执行业务点,本质上是执行chainDesc的invoke()：execute() -> executeFunction() -> invoke()
                    chainDesc.execute();
                    //获取执行信息
                    infoList.add(chainDesc.getInfo());
                    //最后返回值的Code
                    LastRetCode = chainDesc.getRetCode();
                    //返回值送入临时缓存
                    temporaryValueCache.put(chainDesc.getRetCode(),chainDesc.getRetBean());
                }
                return (T)temporaryValueCache.get(LastRetCode);
            }
        } catch (Exception e) {
            throw new BusinessFlowException(e);
        }
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * 无返回类型
     *
     * @throws BusinessFlowException Data异常
     */
    public void start() throws BusinessFlowException {
        try {
            start(Object.class);
        } catch (Exception e) {
            throw new BusinessFlowException(e);
        }
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * 添加业务节点
     *
     * @param bean 处理者bean
     * @param methodCode 处理方法code
     * @param desc 业务点描述
     * @param retCode 返回值key
     * @return {@link BusinessFlow } 链式调用
     * @throws BusinessFlowException 业务流异常
     */
    public BusinessFlow add(Object bean, String methodCode,String desc,String retCode) throws BusinessFlowException {
        try {
            //获得业务点
            ChainDesc chainDesc = ChainDesc.build(bean, methodCode, desc, retCode);
            chainList.offer(chainDesc);
        } catch (Exception e) {
            throw new BusinessFlowException(e);
        }
        return this;
    }

    //bean类型
    public <T> BusinessFlow add(Class<T> beanType, String methodCode) throws BusinessFlowException {
        add(beanType,methodCode,null,null);
        return this;
    }

    //实体bean
    public <T> BusinessFlow add(Object bean, String methodCode) throws BusinessFlowException {
        add(bean,methodCode,null,null);
        return this;
    }


    //bean类型
    public BusinessFlow add(Class<?> beanType, String methodCode,String desc,String retCode) throws BusinessFlowException {
        try {
            //获得业务data
            Object bean = CONTEXT.getData(beanType);
            add(bean, methodCode,desc,retCode);
        } catch (Exception e) {
            throw new BusinessFlowException(e);
        }
        return this;
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * 导入参数
     *
     * 参数：@Source > 参数名，且@Source能从{@link GlobalValueCache}匹配
     * - 支持导入全局缓存{@link GlobalValueCache}和 临时缓存{@link TemporaryValueCache}
     * - 根据{@link Source}从数据源匹配， 优先从{@link GlobalValueCache}匹配
     * - 根据参数名来匹配参数，仅能从{@link TemporaryValueCache}匹配
     *
     * @param parameters
     * @return int
     */
    private Object[] importParams(Parameter[] parameters) throws Exception {
        Object[] ret = new Object[parameters.length];
        for (int i = 0; i < parameters.length; i++) {
            Parameter parameter = parameters[i];
            Source source = parameter.getAnnotation(Source.class);
            ret[i] = parameter.getType().newInstance();

            //传入全局数据缓存
            if(parameter.getType().equals(GlobalValueCache.class)){
                ret[i] = globalValueCache;
                continue;
            }

            //传入临时数据缓存
            if(parameter.getType().equals(TemporaryValueCache.class)){
                ret[i] = temporaryValueCache;
                continue;
            }

            try { //转换失败则跳过，且赋null值
                //有@Source 且 value有值
                if(null != source && StrUtil.isNotBlank(source.value())){
                    try {
                        Converter.conver(ret[i],source.value(),ruleCache,globalValueCache);
                    } catch (ConverException e) {
                        Converter.conver(ret[i],source.value(), ruleCache, temporaryValueCache);
                    }
                //有@Source，但无value
                }else if(null != source){
                    try {
                        Converter.conver(ret[i],parameter.getName(),ruleCache,globalValueCache);
                    } catch (ConverException e) {
                        Converter.conver(ret[i],parameter.getName(), ruleCache, temporaryValueCache);
                    }
                //无@Source
                }else {
                    Converter.conver(ret[i],parameter.getName(), ruleCache, temporaryValueCache);
                }
            } catch (ConverException e) {
                e.printStackTrace();
                ret[i] = null;
            }
        }
        return ret;
    }

    public List<TaskInfo> getInfoList() {
        return infoList;
    }

    public void setInfoList(List<TaskInfo> infoList) {
        this.infoList = infoList;
    }
}
