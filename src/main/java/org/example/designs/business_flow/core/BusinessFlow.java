package org.example.designs.business_flow.core;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.example.designs.Info.InfoConstant;
import org.example.designs.business_flow.annotation.Source;
import org.example.designs.business_flow.cache.GlobalCache;
import org.example.designs.business_flow.cache.TemporaryCache;
import org.example.designs.business_flow.context.IContext;
import org.example.designs.business_flow.context.SpringBeanContext;
import org.example.designs.conver.core.DataRules;
import org.example.designs.conver.core.ConverException;
import org.example.designs.conver.core.Converter;
import org.example.designs.business_flow.desc.ChainDesc;
import org.example.designs.task.TaskException;
import org.example.designs.Info.InfoCache;
import org.slf4j.MDC;

import java.lang.reflect.Parameter;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * TODO
 *
 * <p>
 *     TODO
 * </p>
 *
 * @author HaiYu
 * @date 2024-11-22@version 1.0.0
 */
@Slf4j
public class BusinessFlow {

    /**一些配置项
     */
    //是否记录参数和返回值
    private boolean isRecordParamAndRet = false;
    public BusinessFlow recordParamAndRet(boolean isRecordParamAndRet){
        this.isRecordParamAndRet = isRecordParamAndRet;
        return this;
    }

    //是否可视化打印日志
    private boolean isPrettyPrint = false;

    public BusinessFlow prettyPrint(boolean isPrettyPrint){
        this.isPrettyPrint = isPrettyPrint;
        return this;
    }

    //业务流的锁
    private final Object startupShutdownMonitor;
    //bean容器
    static private IContext context = new SpringBeanContext();
    //全局数据的缓存，整条业务流程里的缓存
    private GlobalCache globalCache;
    //临时数据的缓存，只传递一个业务点
    private TemporaryCache temporaryCache;
    //转换规则缓存
    private DataRules ruleCache;


    /*
     *  业务流基本信息
     */
    //业务流额外信息
    private Map<String,Object> businessFlowInfo = new LinkedHashMap<>();
    //业务点列表
    private Queue<ChainDesc> chainQueue = new LinkedList<>();
    //起始时间
    private LocalDateTime startTime;
    //结束时间
    private LocalDateTime endTime;
    //业务执行信息列表
    private List<Map<String, Object>> chainInfoList;
    //基础信息
    private InfoCache info;


    //构造方法私有
    private BusinessFlow(InfoCache info) {
        this.startupShutdownMonitor = new Object();
        this.chainInfoList = new ArrayList<>();
        this.globalCache = new GlobalCache();
        this.temporaryCache = new TemporaryCache();
        this.ruleCache = new DataRules();
        this.info = info;
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * 构建方法
     *
     * @param desc 业务流描述
     * @return {@link BusinessFlow }
     */
    public static BusinessFlow build(String desc){
        InfoCache info = InfoCache.build(desc);
        info.nothing();
        info.putInfo("executeFailed",new AtomicInteger(0));
        BusinessFlow businessFlow = new BusinessFlow(info);
        return businessFlow;
    }

    public static BusinessFlow build(InfoCache info){
        BusinessFlow businessFlow = new BusinessFlow(info);
        return businessFlow;
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * 解析方法字符串为方法名
     *
     * "Student.getName()" -> "getName"
     * "Student::getName"  -> "getName"
     *  这样可以有效避免方法名写错的问题
     *
     * @param method 方法字符串
     * @return {@link String }
     */
    private String analysisMethod(String method){
        String[] strings = method.split(":|\\.");
        method = strings[strings.length-1].replaceAll("（.*?）|\\(.*?\\)", "");
        return method;
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
            methodCode = analysisMethod(methodCode);
            //获得业务点
            ChainDesc chainDesc = ChainDesc.build(bean, methodCode, desc, retCode);
            chainQueue.offer(chainDesc);
        } catch (Exception e) {
            throw new BusinessFlowException("业务流["+this.info.getDesc()+"]的method["+methodCode+"]添加失败",e);
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
            throw new BusinessFlowException("业务流["+this.info.getDesc()+"]的Bean["+beanType.getName()+"]在Bean容器里不存在",e);
        }
        add(bean, methodCode,desc,retCode);

        return this;
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * 添加业务点，通过匿名类
     *
     * @param desc
     * @param retCode
     * @param chain
     * @return {@link BusinessFlow }
     * @throws BusinessFlowException Data异常
     */
    public BusinessFlow add(String desc,String retCode,IChain chain) throws BusinessFlowException {
        add(chain,"method",desc,retCode);
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
     * 启动业务流，但不结束（有返回值）
     *
     * @return {@link BusinessFlow }
     * @throws BusinessFlowException Data异常
     */
    public BusinessFlow intercept() throws BusinessFlowException {
        //每个业务流启动时，都应该有一个唯一标识
        String uuid = UUID.randomUUID().toString();
        info.putInfo("uuid",uuid);
        if(StrUtil.isBlank(MDC.get("businessID"))) MDC.put("businessID",uuid);
        ChainDesc chainDesc = null;
        Parameter[] parameters = null;
        try {
            synchronized (startupShutdownMonitor) {
                info.startTime();
                info.executing();
                while (!chainQueue.isEmpty()) {
                    //获得业务点
                    chainDesc = chainQueue.poll();

                    //业务点所需参数
                    parameters = chainDesc.getParameters();

                    //参数赋值
                    try {
                        chainDesc.setParams(importParams(parameters));
                    } catch (Exception e) {
                        throw new BusinessFlowException(String.format("业务点[%s][%s]传参失败",chainDesc.getDesc(),chainDesc.getMethod().getName()),e);
                    }
                    //获取参数后，清除临时缓存
                    temporaryCache.clear();

                    //执行业务点,本质上是执行chainDesc的invoke()：execute() -> executeFunction() -> invoke()
                    try {
                        info.count("executeChain");
                        chainDesc.execute();
                    } catch (TaskException e) {
                        info.count("executeFailed");
                        throw new BusinessFlowException(String.format("业务点[%s][%s]执行失败",chainDesc.getDesc(),chainDesc.getMethod().getName()),e);
                    }

                    //获取执行信息
                    InfoCache chainInfo = chainDesc.getInfo();
                    log.info(isPrettyPrint ?chainInfo.toJsonPretty() :chainInfo.toJson());
                    chainInfoList.add(chainInfo.getInfo());

                    //返回值送入临时缓存
                    if(null != chainDesc.getRetBean()){
                        temporaryCache.put(chainDesc.getRetCode(), chainDesc.getRetBean());
                    }
                }
                return this;
            }
        } catch (Exception e) {
            //获取执行信息
            this.info.endTime();
            this.info.exceptional();
            InfoCache chainInfo = chainDesc.getInfo();
            log.error(isPrettyPrint ?chainInfo.toJsonPretty() :chainInfo.toJson());
            chainInfoList.add(chainInfo.getInfo());
//            log.info(JSONUtil.toJsonStr(isRecordParamAndRet?chainInfo:info));
            throw new BusinessFlowException(String.format("业务流[%s]执行出错",this.info.getDesc()),e);
        }
    }
    public BusinessFlow end() throws BusinessFlowException {
        try {
            intercept();
            this.info.success();
        } finally {
            this.info.endTime();
            if(info.isSuccess()){
                log.info(isPrettyPrint?info.toJsonPretty():info.toJson());
            }else {
                log.error(isPrettyPrint?info.toJsonPretty():info.toJson());
            }
            MDC.remove("businessID");

        }
        return this;
    }



    /**
     * -----------------------------------------------------------------------------------------------------------------
     * 自动传参
     *
     * 参数：@Source > 参数名，且@Source能从{@link GlobalCache}匹配
     * - 支持导入全局缓存{@link GlobalCache}和 临时缓存{@link TemporaryCache}
     * - 根据{@link Source}从数据源匹配， 优先从{@link GlobalCache}匹配
     * - 根据参数名来匹配参数，仅能从{@link TemporaryCache}匹配
     *
     * @param parameters
     * @return int
     */
    private Object[] importParams(Parameter[] parameters) throws Exception {
        Object[] ret = new Object[parameters.length];
        for (int i = 0; i < parameters.length; i++) {
            Parameter parameter = parameters[i];

            //传入全局数据缓存
            if(parameter.getType().equals(GlobalCache.class)){
                ret[i] = globalCache;
                continue;
            }

            //传入临时数据缓存
            if(parameter.getType().equals(TemporaryCache.class)){
                ret[i] = temporaryCache;
                continue;
            }

            //获得注解
            Source source = parameter.getAnnotation(Source.class);
            try { //传参失败则跳过，且赋null值
                //有@Source 且 value有值
                if(null != source && StrUtil.isNotBlank(source.value())){
                    ret[i] = Converter.conver(parameter.getType(),source.value(),ruleCache, globalCache,false);
                    if(null == ret[i]) ret[i] = Converter.conver(parameter.getType(),source.value(),ruleCache, temporaryCache,false);
                //有@Source，但无value
                }else if(null != source){
                    ret[i] = Converter.conver(parameter.getType(),parameter.getName(),ruleCache, globalCache,false);
                    if(null == ret[i]) ret[i] = Converter.conver(parameter.getType(),parameter.getName(),ruleCache, temporaryCache,false);
                //无@Source
                }else {
                    ret[i] = Converter.conver(parameter.getType(),parameter.getName(), ruleCache, temporaryCache,false);
                }
            } catch (ConverException e) {
                log.warn(String.format("参数[%s]转换失败, 现传空值",parameter.getName()));
                ret[i] = null;
            }
        }
        return ret;
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * 获取JSON格式的日志信息
     *
     * @return {@link String }
     */
    public String getInfoLog(){
        Map<String, Object> info1 = info.getInfo();
        info1.put("zz_chainInfos",chainInfoList);
        return JSONUtil.toJsonStr(info1);
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * 获得可读性高的JSON格式的信息
     *
     * @return {@link String }
     */
    public String getVisualLog(){
        Map<String, Object> info1 = info.getInfo();
        info1.put("zz_chainInfos",chainInfoList);
        return JSONUtil.toJsonPrettyStr(info1);
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
     * 临时和全局缓存的put和get
     *
     * @param key
     * @param value
     * @return {@link Object }
     */
    public Object putTemporary(String key, Object value){
        return temporaryCache.put(key,value);
    }
    public void putTemporary(Map<String,Object> dataMap){
        temporaryCache.putAll(dataMap);
    }
    public Object getTemporary(String key){
        return temporaryCache.get(key);
    }
    public Object putGlobal(String key, Object value){
        return globalCache.put(key,value);
    }
    public void putGlobal(Map<String,Object> dataMap){
        globalCache.putAll(dataMap);
    }
    public Object getGlobal(String key){
        return globalCache.get(key);
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

    public Queue<ChainDesc> getChainQueue() {
        return chainQueue;
    }

    public void setChainQueue(Queue<ChainDesc> chainQueue) {
        this.chainQueue = chainQueue;
    }

    public List<Map<String, Object>> getChainInfoList() {
        return chainInfoList;
    }

    public void setChainInfoList(List<Map<String, Object>> chainInfoList) {
        this.chainInfoList = chainInfoList;
    }

    public GlobalCache getGlobalValueCache() {
        return globalCache;
    }

    public void setGlobalValueCache(GlobalCache globalCache) {
        this.globalCache = globalCache;
    }

    public TemporaryCache getTemporaryValueCache() {
        return temporaryCache;
    }

    public void setTemporaryValueCache(TemporaryCache temporaryCache) {
        this.temporaryCache = temporaryCache;
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
        return this.info.getDesc();
    }

    public void setDesc(String desc) {
        this.info.putInfo("desc",desc);
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

    public GlobalCache getGlobalCache() {
        return globalCache;
    }

    public void setGlobalCache(GlobalCache globalCache) {
        this.globalCache = globalCache;
    }

    public TemporaryCache getTemporaryCache() {
        return temporaryCache;
    }

    public void setTemporaryCache(TemporaryCache temporaryCache) {
        this.temporaryCache = temporaryCache;
    }
}
