package org.example.designs.business_flow.core;

import cn.hutool.core.util.StrUtil;
import org.example.designs.business_flow.annotation.Chain;
import org.example.designs.business_flow.annotation.Source;
import org.example.designs.business_flow.cache.GlobalValueCache;
import org.example.designs.business_flow.cache.TemporaryValueCache;
import org.example.designs.business_flow.context.IContext;
import org.example.designs.business_flow.context.SpringBeanContext;
import org.example.designs.conver.core.DataRuleMap;
import org.example.designs.conver.core.ConverException;
import org.example.designs.conver.core.Converter;
import org.example.designs.business_flow.desc.ChainDesc;
import org.example.designs.task.AbstractTask;
import org.example.designs.task.TaskInfo;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
public class BusinessFlow extends AbstractTask {
    //data容器内
    static private IContext CONTEXT = new SpringBeanContext();
    
    //业务执行信息列表
    private List<TaskInfo> infoList;
    //全局数据的缓存，整条业务流程里的缓存
    private GlobalValueCache globalValueCache;
    //临时数据的缓存，只传递一次
    private TemporaryValueCache temporaryValueCache;
    //转换规则缓存
    private DataRuleMap ruleCache;
    //当前ChainDesc
    private ChainDesc curChain;

    //构造方法私有
    private BusinessFlow() {
        this.infoList = new ArrayList<>();
        this.globalValueCache = new GlobalValueCache();
        this.temporaryValueCache = new TemporaryValueCache();
        this.ruleCache = new DataRuleMap();
        this.curChain = null;
    }

    @Override
    public boolean executeFunction(Map<String,Object> params) throws Exception {
        Class<?> dataType = (Class<?>) params.get("dataType");
        String methodCode = (String) params.get("methodCode");
        String desc = (String) params.get("desc");
        String retCode = (String) params.get("retCode");
        //获得业务data
        Object data = CONTEXT.getData(dataType);
        //获得业务点
        this.curChain = ChainDesc.build(data,methodCode,desc,retCode);
        //业务点所需参数
        Parameter[] parameters = curChain.getParameters();
        //参数赋值
        curChain.setParams(importParams(parameters));
        //获取参数后，清除临时缓存
        temporaryValueCache.clear();
        //执行业务点
        Object ret = curChain.invoke();
        //返回值送入临时缓存
        temporaryValueCache.put(curChain.getRetCode(),ret);
        return true;
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
     * @return {@link BusinessFlow }
     */
    public BusinessFlow start(){
        return this;
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * 结束方法
     *
     * @param retType
     * @return {@link T }
     * @throws BusinessFlowException Data异常
     */
    public <T> T end(Class<T> retType) throws BusinessFlowException {
        T ret = null;
        try {
            ret = (T)temporaryValueCache.get(curChain.getRetCode());
        } catch (Exception e) {
            throw new BusinessFlowException(e);
        }
        return ret;
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * 添加业务点
     *
     * @param dataType   处理者类型
     * @param methodCode 处理方法code
     * @param desc       业务点描述
     * @return {@link BusinessFlow }
     * @throws BusinessFlowException 业务流异常
     */
    public BusinessFlow add(Class<?> dataType, String methodCode,String desc,String retCode) throws BusinessFlowException {
        try {
            HashMap<String, Object> params = new HashMap<>();
            params.put("dataType",dataType);
            params.put("methodCode",methodCode);
            params.put("desc",desc);
            params.put("retCode",retCode);
            //execute的本质就是调用上面的executeFunction，只是套了一些东西进去，用于获取执行信息
            execute(params);
            //获取执行信息
            TaskInfo info = getInfo();
            info.setDescribe(curChain.getDesc());
            infoList.add(info);
        } catch (Exception e) {
            throw new BusinessFlowException(e);
        }
        return this;
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * 添加业务节点
     *
     * @param dataType 处理者类型
     * @param methodCode 处理方法code
     * @return {@link BusinessFlow }
     */
    public <T> BusinessFlow add(Class<T> dataType, String methodCode) throws BusinessFlowException {
        add(dataType,methodCode,null,null);
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
