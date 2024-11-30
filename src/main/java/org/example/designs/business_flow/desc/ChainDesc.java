package org.example.designs.business_flow.desc;

import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import org.example.designs.business_flow.annotation.Chain;
import org.example.designs.business_flow.core.BusinessFlowException;
import org.example.designs.business_flow.core.IChain;
import org.example.designs.task.AbstractTask;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Map;

/**
 * 业务点描述
 *
 * <p>
 *     每个业务点都可以抽象理解为一个方法,所有功能都是围绕这个方法去实现
 *     业务bean(bean),业务点方法(Method),方法编码(@Chain的value值),业务点描述(@Chain的desc值)
 * </p>
 *
 * @author HaiYu
 * @version 1.0.0
 * @create 2024-11-19
 */
 public class ChainDesc extends AbstractTask {
     //业务处理者
    private Object bean;
    //处理方法
    private Method method;
    //参数列表
    private Parameter[] parameters;
    //参数列表
    private Object[] params;
    //方法值code，存入数据缓存时的key
    private String retCode;
    //返回bean
    private Object retBean;
    //业务点执行的信息
    private ChainInfo chainInfo;


    private ChainDesc(Object bean, Method method, String desc, String retCode) {
        this.bean = bean;
        this.method = method;
        super.taskInfo.desc = desc;
        this.retCode = retCode;
        this.parameters = method.getParameters();
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * 执行业务点
     *
     * @param params
     * @return boolean
     * @throws Exception
     */
    @Override
    public boolean executeFunction(Map<String, Object> params) throws Exception {
        this.retBean = invoke();
        return true;
    }



    /** ---------------------------------------------------------------------------------------------------------------------
     * 构建一个ChainDesc
     *
     * 业务点描述: 传入desc > @Chain的desc
     *
     * @param  bean   业务点处理者
     * @param  methodCode 业务点处理方法编码
     * @param  desc 业务点描述
     * @return ChainDesc 业务点描述
     */
    public static ChainDesc build(Object bean, String methodCode,String retCode, String desc) throws BusinessFlowException {
        ChainMethodDesc chainMethodDesc = getChainMethod(bean.getClass(), methodCode);

        if(StrUtil.isNotBlank(desc)){
            chainMethodDesc.setDesc(desc);
        }
        if(StrUtil.isNotBlank(retCode)){
            chainMethodDesc.setRetCode(retCode);
        }

        return new ChainDesc(bean
                , chainMethodDesc.getMethod()
                ,chainMethodDesc.getDesc()
                ,chainMethodDesc.getRetCode());
    }


    /** ---------------------------------------------------------------------------------------------------------------------
     * 获取业务点的处理方法
     *
     * 匹配{@link Chain}注解过的方法
     * method匹配的优先级：首位@Chain.Code() > @Chain.Code() > 尾位@Chain
     * desc匹配的优先级: @Chain.value() > 传入的desc
     * @param  dataType data类型
     * @param  methodCode 方法编号（方法名）
     * @return Method   方法
     */
    private static <T> ChainMethodDesc getChainMethod(Class<T> dataType, String methodCode) throws BusinessFlowException {
        Method[] methods = ReflectUtil.getPublicMethods(dataType);
        ChainMethodDesc ret = null;
        if(IChain.class.isAssignableFrom(dataType)){
            try {
                methods[0].setAccessible(true);
                return new ChainMethodDesc(methods[0],null,null);
            } catch (Exception e) {
                throw new BusinessFlowException(String.format("没有找到方法[%s]",methodCode),e);
            }
        }
        for (Method method : methods) {
            if (method.isAnnotationPresent(Chain.class)) {
                // 获取 Chain 注解实例
                Chain chain = method.getAnnotation(Chain.class);
                // 首位@Chain.Code()
                if(null != chain && chain.code().equals(methodCode)){
                    return new ChainMethodDesc(method,chain.desc(),chain.retCode());
                // 尾位@Chain
                }else if (null != chain && method.getName().equals(methodCode)){
                    ret = new ChainMethodDesc(method,chain.desc(),chain.retCode());
                }
            }
        }
        if(null == ret) {
            throw new BusinessFlowException(dataType.getName()+":@Chain的code错误，或方法["+methodCode+"]没有使用@Chain");
        }
        return ret;
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * 执行对象的指定方法
     *
     * @return 返回类型
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @throws BusinessFlowException
     */
    public Object invoke() throws InvocationTargetException, IllegalAccessException, BusinessFlowException {
        if(null == this.bean || null == this.method){
            throw new BusinessFlowException("data or method is null");
        }
        return this.method.invoke(bean,this.params);
    }


    public ChainInfo getChainInfo() {
        return chainInfo;
    }

    public void setChainInfo(ChainInfo chainInfo) {
        this.chainInfo = chainInfo;
    }

    public Parameter[] getParameters() {
        return parameters;
    }

    public void setParameters(Parameter[] parameters) {
        this.parameters = parameters;
    }

    public Method getMethod() {
        return method;
    }

    public String getDesc() {
        return super.taskInfo.desc;
    }

    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

    public Object getBean() {
        return bean;
    }

    public void setBean(Object bean) {
        this.bean = bean;
    }

    public Method getChainMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public void setDesc(String desc) {
        this.taskInfo.desc = desc;
    }

    public Object[] getParams() {
        return params;
    }

    public void setParams(Object[] params) {
        this.params = params;
    }

    public Object getRetBean() {
        return retBean;
    }

    public void setRetBean(Object retBean) {
        this.retBean = retBean;
    }


}
