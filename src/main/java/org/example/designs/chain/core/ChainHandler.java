package org.example.designs.chain.core;

import org.example.designs.chain.cache.GlobalCache;
import org.example.designs.chain.cache.TemporaryCache;
import org.example.designs.chain.context.BeanException;
import org.example.designs.chain.context.IContext;
import org.example.designs.chain.context.SpringBeanContext;
import org.example.designs.conver.BeanRule;
import org.example.designs.conver.Converter;
import org.example.designs.chain.desc.ChainDesc;
import org.example.designs.task.AbstractTask;
import org.example.designs.task.TaskInfo;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;


/**
 * @Program: 8.mindmap
 * @Author: 海里的鱼
 * @Create: 2024-09-10 10:30
 * @Name: 链路处理者
 * @Desc: TODO
 */
public class ChainHandler extends AbstractTask {
    //链路列表
    private List<TaskInfo> infoList = new ArrayList<>();
    //全局数据的缓存，真条业务流程里的缓存
    private GlobalCache globalCache = new GlobalCache();
    //临时数据的缓存，只传递一次
    private TemporaryCache temporaryCache = new TemporaryCache();
    //转换规则缓存
    private BeanRule ruleCache = new BeanRule();
    //业务逻辑
    private BiFunction<Map<String,Object>,Map<String,Object>,Object> process;
    //Bean容器
    private IContext context = new SpringBeanContext();


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * 添加业务节点
     *
     * @param beanType 处理者类型
     * @param methodCode 处理方法code
     * @param desc 业务点描述
     * @return {@link ChainHandler }
     */
    public <T> ChainHandler add(Class<T> beanType, String methodCode, String desc) throws NoSuchFieldException, IllegalAccessException, InvocationTargetException {
        try {
            //获得业务处理者
            T bean = context.getBean(beanType);
            //获得业务点
            ChainDesc chainDesc = ChainDesc.getDesc(bean,methodCode,desc);
            //业务点所需参数
            Parameter[] parameters = chainDesc.getParameters();
            //转换器为参数赋值
            Object[] params = Converter.converBatch(parameters, ruleCache);
            //执行业务点
            chainDesc.setParams(params);
            Object ret = chainDesc.invoke();
            System.out.println(ret);
        } catch (BeanException e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * 添加业务节点
     *
     * @param beanType 处理者类型
     * @param methodCode 处理方法code
     * @return {@link ChainHandler }
     */
    public <T> ChainHandler add(Class<T> beanType,String methodCode) throws NoSuchFieldException, InvocationTargetException, IllegalAccessException {
        add(beanType,methodCode,null);
        return this;
    }

    @Override
    public boolean executeFunction() throws Exception {
//        Object response = process.apply(temporaryMap, GlobalMap);
//        temporaryMap = BeanUtil.beanToMap(response,false,false);
//        return true;
        return false;
    }


//    public <T extends IChainNode> ChainHandler add(Class<T> nodeType, String describe) throws Exception {
//        IChainNode node = AppContextUtil.getBean(nodeType);
//        return add(node::process,nodeType.getName(),describe);
//    }
//
//    public ChainHandler add(BiFunction<Map<String,Object>,Map<String,Object>,Object> process, String describe) throws Exception {
//        return add(process,process.toString(),describe);
//    }
//
//    public ChainHandler add(BiFunction<Map<String,Object>,Map<String,Object>,Object> process,String name ,String describe) throws Exception {
//        this.name = name;
//        this.describe = describe;
//        this.process = process ;
//        execute();
//        infoList.add(getInfo());
//        return this;
//    }

//    public ChainHandler putTemporary(String Key,Object value){
//        this.temporaryMap.put(Key,value);
//        return this;
//    }
//
//    public ChainHandler putGlobalMap(String Key,Object value){
//        this.GlobalMap.put(Key,value);
//        return this;
//    }




}
