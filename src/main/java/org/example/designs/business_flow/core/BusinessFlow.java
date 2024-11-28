package org.example.designs.business_flow.core;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONConfig;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.example.designs.business_flow.annotation.Chain;
import org.example.designs.business_flow.annotation.Source;
import org.example.designs.business_flow.cache.GlobalValueCache;
import org.example.designs.business_flow.cache.TemporaryValueCache;
import org.example.designs.business_flow.context.IContext;
import org.example.designs.business_flow.context.SpringBeanContext;
import org.example.designs.business_flow.desc.ChainInfo;
import org.example.designs.conver.core.DataRules;
import org.example.designs.conver.core.ConverException;
import org.example.designs.conver.core.Converter;
import org.example.designs.business_flow.desc.ChainDesc;
import org.example.designs.task.TaskInfo;
import java.lang.reflect.Parameter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;


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
@Slf4j
public class BusinessFlow {
    //业务流的锁
    private final Object startupShutdownMonitor;
    //bean容器
    static private IContext context = new SpringBeanContext();
    //全局数据的缓存，整条业务流程里的缓存
    private GlobalValueCache globalValueCache;
    //临时数据的缓存，只传递一次
    private TemporaryValueCache temporaryValueCache;
    //转换规则缓存
    private DataRules ruleCache;
    //日志是否自动打印
    private boolean autoPrintLog = false;
    //可视化日志是否自动打印
    private boolean autoPrintVisualLog = false;

    /*
     *  业务流基本信息
     */
    //业务流额外信息
    private Map<String,Object> businessFlowInfo = new LinkedHashMap<>();
    //业务流描述
    private String desc;
    //业务点列表
    private Queue<ChainDesc> chainList = new LinkedList<>();
    //起始时间
    private LocalDateTime startTime;
    //结束时间
    private LocalDateTime endTime;
    //业务流最终返回值
    private Object ret;
    //业务执行信息列表
    private List<ChainInfo> chainInfoList;


    //构造方法私有
    private BusinessFlow() {
        this.startupShutdownMonitor = new Object();
        this.chainInfoList = new ArrayList<>();
        this.globalValueCache = new GlobalValueCache();
        this.temporaryValueCache = new TemporaryValueCache();
        this.ruleCache = new DataRules();
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * 构建方法
     *
     * @param desc 业务流描述
     * @return {@link BusinessFlow }
     */
    public static BusinessFlow build(String desc){
        BusinessFlow businessFlow = new BusinessFlow();
        businessFlow.setDesc(desc);
        return businessFlow;
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
            throw new BusinessFlowException("业务流["+this.desc+"]的method["+methodCode+"]添加失败",e);
        }
        return this;
    }

    //bean类型,方法,desc,retCode
    public BusinessFlow add(Class<?> beanType, String methodCode,String desc,String retCode) throws BusinessFlowException {
        Object bean = null;
        try {
            //获得业务bean
            bean = context.getData(beanType);
        } catch (Exception e) {
            throw new BusinessFlowException("业务流["+this.desc+"]的Bean["+beanType.getName()+"]在Bean容器里不存在",e);
        }
        add(bean, methodCode,desc,retCode);

        return this;
    }

    //实体bean,方法
    public <T> BusinessFlow add(Object bean, String methodCode) throws BusinessFlowException {
        add(bean,methodCode,null,null);
        return this;
    }

    //bean类型,方法
    public <T> BusinessFlow add(Class<T> beanType, String methodCode) throws BusinessFlowException {
        add(beanType,methodCode,null,null);
        return this;
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * 启动业务流（有返回值）
     *
     * @param retType 业务流返回值类型
     * @return {@link BusinessFlow }
     * @throws BusinessFlowException Data异常
     */
    public <T> T start(Class<T> retType) throws BusinessFlowException {
        ChainDesc chainDesc = null;
        Parameter[] parameters = null;
        try {
            synchronized (startupShutdownMonitor) {
                this.startTime = LocalDateTime.now();
                String lastRetCode = null;
                while (!chainList.isEmpty()) {
                    chainDesc = chainList.poll();
                    //业务点所需参数
                    parameters = chainDesc.getParameters();
                    //参数赋值
                    chainDesc.setParams(importParams(parameters));
                    //获取参数后，清除临时缓存
                    temporaryValueCache.clear();
                    //执行业务点,本质上是执行chainDesc的invoke()：execute() -> executeFunction() -> invoke()
                    chainDesc.execute();
                    //获取执行信息
                    TaskInfo info = chainDesc.getInfo();
                    chainInfoList.add(new ChainInfo(parameters, chainDesc.getRetBean(), info));
                    //最后返回值的Code
                    lastRetCode = chainDesc.getRetCode();
                    //返回值送入临时缓存
                    temporaryValueCache.put(chainDesc.getRetCode(), chainDesc.getRetBean());
                }
                //是否有返回类型,没有就代表不需要返回值
                if (null == retType) {
                    this.ret = null;
                    return null;
                //没有找到返回值
                } else if (null == temporaryValueCache.get(lastRetCode) && null == globalValueCache.get(lastRetCode)) {
                    throw new BusinessFlowException("业务流["+this.desc+"]找不到返回值,retCode为[" + lastRetCode + "],类型为[" + retType.getName() + "]");
                }
                this.ret = globalValueCache.get(lastRetCode);
                if (null == this.ret) this.ret = temporaryValueCache.get(lastRetCode);
                return (T) ret;
            }
        } catch (Exception e) {
            //获取执行信息
            TaskInfo info = chainDesc.getInfo();
            chainInfoList.add(new ChainInfo(parameters, chainDesc.getRetBean(), info));
            throw new BusinessFlowException("业务流["+this.desc+"]执行出错",e);
        }finally {
            this.endTime = LocalDateTime.now();
            if(autoPrintLog) log.info(getInfoJSONLog());
            if(autoPrintVisualLog) log.info(getVisualJSONLog());
        }
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * 启动业务流（无返回值）
     *
     * @throws BusinessFlowException Data异常
     */
    public void start() throws BusinessFlowException {
            start(null);
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * 自动传参
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
            //获得注解
            Source source = parameter.getAnnotation(Source.class);
//            try {
//                ret[i] = (parameter.getType().isPrimitive()) ? new Object() : parameter.getType().newInstance();
//            } catch (Exception e) {
//                throw new BusinessFlowException("param["+parameter.getName()+"]实例化失败",e);
//            }

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

            try { //传参失败则跳过，且赋null值
                //有@Source 且 value有值
                if(null != source && StrUtil.isNotBlank(source.value())){
                    ret[i] = Converter.conver(parameter.getType(),source.value(),ruleCache,globalValueCache,false);
                    if(null == ret[i]) ret[i] = Converter.conver(parameter.getType(),source.value(),ruleCache,temporaryValueCache,false);
                //有@Source，但无value
                }else if(null != source){
                    ret[i] = Converter.conver(parameter.getType(),parameter.getName(),ruleCache,globalValueCache,false);
                    if(null == ret[i]) ret[i] = Converter.conver(parameter.getType(),parameter.getName(),ruleCache,temporaryValueCache,false);
                //无@Source
                }else {
                    ret[i] = Converter.conver(parameter.getType(),parameter.getName(), ruleCache, temporaryValueCache,false);
                }
            } catch (ConverException e) {
                log.warn("参数["+parameter.getName()+"]转换失败, 现传空值");
                ret[i] = null;
            }
        }
        return ret;
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * 给业务流添加信息,用于打印时显示
     *
     * @param key
     * @param value
     * @return {@link Object }
     */
    public Object putInfo(String key, Object value){
        return businessFlowInfo.put(key,value);
    }

    public void putInfo(Map<String,Object> infoMap){
       businessFlowInfo.putAll(infoMap);
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * 获取JSON格式的日志信息
     *
     * @return {@link String }
     */
    public String getInfoJSONLog(){
        businessFlowInfo.put("desc",desc);
        businessFlowInfo.put("chainCount", chainInfoList.size());
        businessFlowInfo.put("startTime",startTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        businessFlowInfo.put("endTime",endTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        businessFlowInfo.put("runningTime", ChronoUnit.MILLIS.between(startTime, endTime));
        businessFlowInfo.put("ret",(null == this.ret?"null":ret));
        businessFlowInfo.put("chainInfo", chainInfoList);
        JSONConfig config = JSONConfig.create()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .setIgnoreNullValue(false);
        return JSONUtil.toJsonStr(businessFlowInfo,config);
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * 获得可读性高的JSON格式的日志信息
     *
     * @return {@link String }
     */
    public String getVisualJSONLog(){
        return JSONUtil.toJsonPrettyStr(getInfoJSONLog());
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * 获得运行时间
     *
     * @return {@link Long }
     */
    public Long getRunningTime() {
        if(startTime == null || endTime == null){
            return -1L;
        }
        return ChronoUnit.MILLIS.between(startTime, endTime);
    }

    public static IContext getContext() {
        return context;
    }

    public static void setContext(IContext context) {
        BusinessFlow.context = context;
    }

    public Queue<ChainDesc> getChainList() {
        return chainList;
    }

    public void setChainList(Queue<ChainDesc> chainList) {
        this.chainList = chainList;
    }

    public List<ChainInfo> getChainInfoList() {
        return chainInfoList;
    }

    public void setChainInfoList(List<ChainInfo> chainInfoList) {
        this.chainInfoList = chainInfoList;
    }

    public GlobalValueCache getGlobalValueCache() {
        return globalValueCache;
    }

    public void setGlobalValueCache(GlobalValueCache globalValueCache) {
        this.globalValueCache = globalValueCache;
    }

    public TemporaryValueCache getTemporaryValueCache() {
        return temporaryValueCache;
    }

    public void setTemporaryValueCache(TemporaryValueCache temporaryValueCache) {
        this.temporaryValueCache = temporaryValueCache;
    }

    public DataRules getRuleCache() {
        return ruleCache;
    }

    public void setRuleCache(DataRules ruleCache) {
        this.ruleCache = ruleCache;
    }

    public Map<String, Object> getBusinessFlowInfo() {
        return businessFlowInfo;
    }

    public void setBusinessFlowInfo(Map<String, Object> businessFlowInfo) {
        this.businessFlowInfo = businessFlowInfo;
    }

    public Object getStartupShutdownMonitor() {
        return startupShutdownMonitor;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public Object getRet() {
        return ret;
    }

    public void setRet(Object ret) {
        this.ret = ret;
    }

    public boolean isAutoPrintLog() {
        return autoPrintLog;
    }

    public void setAutoPrintLog(boolean autoPrintLog) {
        this.autoPrintLog = autoPrintLog;
    }

    public boolean isAutoPrintVisualLog() {
        return autoPrintVisualLog;
    }

    public void setAutoPrintVisualLog(boolean autoPrintVisualLog) {
        this.autoPrintVisualLog = autoPrintVisualLog;
    }
}
