package org.example.designs.chain.desc;

import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import org.example.designs.chain.annotation.Chain;
import org.example.designs.chain.context.BeanException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * 业务点描述
 *
 * <p>
 *     每个业务点都可以抽象理解为一个方法,所有功能都是围绕这个方法去实现
 *     业务点处理者(bean),处理方法(Method),方法编码(@Chain的value值),方法描述(@Chain的desc值)
 * </p>
 *
 * @author HaiYu
 * @version 1.0.0
 * @create 2024-11-19
 */
 public class ChainDesc {

    //业务处理者
    private Object bean;
    //处理方法
    private Method method;
    //方法描述
    private String desc;
    //参数列表
    private Parameter[] parameters;
    //参数列表
    private Object[] params;
    //方法返回类型
    private Class<?> returnType;

    /** ----------------------------------------------------------------------------------------------------------------
     * 通过注解获取信息
     *
     * @param  bean   业务点处理者
     * @param  method 业务点处理方法
     */
    public ChainDesc(Object bean, Method method,String desc){
        this.bean = bean;
        this.method = method;
        this.desc = desc;
        this.returnType = method.getReturnType();
        this.parameters = method.getParameters();
    }

    /** ---------------------------------------------------------------------------------------------------------------------
     * 获取一个ChainDesc
     *
     * @param  bean   业务点处理者
     * @param  methodCode 业务点处理方法编码
     * @param  desc 业务点描述
     * @return ChainDesc 业务点描述
     */
    public static <T> ChainDesc getDesc(T bean, String methodCode, String desc) throws BeanException {
        MethodDesc methodDesc = getMethod(bean.getClass(), methodCode);
        if(StrUtil.isBlank(methodDesc.getDesc())){
            methodDesc.setDesc(desc);
        }
        return new ChainDesc(bean,methodDesc.getMethod(),methodDesc.getDesc());
    }


    /** ---------------------------------------------------------------------------------------------------------------------
     * 通过反射获取业务点描述
     *
     * 优先获取{@Chain}注解过的方法，如果没有注解，则取第一个方法名匹配的方法
     * method匹配的优先级：首位@Chain.Code() > @Chain.Code() > 尾位@Chain > methodName
     * desc匹配的优先级: @Chain.value() > 传入的desc
     * @param  beanType bean类型
     * @param  methodCode 方法编号（方法名）
     * @return Method   方法
     */
    private static <T> MethodDesc getMethod(Class<T> beanType,String methodCode) throws BeanException {
        Method[] methods = ReflectUtil.getPublicMethods(beanType);
        MethodDesc ret = null;
        for (Method method : methods) {
            if (method.isAnnotationPresent(Chain.class)) {
                // 获取 Chain 注解实例
                Chain chain = method.getAnnotation(Chain.class);
                // 首位@Chain.Code()
                if(null != chain && chain.code().equals(methodCode)){
                    return new MethodDesc(method,chain.value());
                // 尾位@Chain
                }else if (null != chain && method.getName().equals(methodCode)){
                    ret = new MethodDesc(method,chain.value());
                // methodName
                }else if(null == ret && method.getName().equals(methodCode)){
                    ret = new MethodDesc(method,null);
                }
            }
        }
        if(null == ret) {
            throw new BeanException(beanType.getName()+":@Chain的code错误，或方法["+methodCode+"]不存在");
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
     * @throws BeanException
     */
    public Object invoke() throws InvocationTargetException, IllegalAccessException, BeanException {
        if(null == this.bean || null == this.method){
            throw new BeanException("bean or method is null");
        }
        return this.method.invoke(bean,this.params);
    }


    public Parameter[] getParameters() {
        return parameters;
    }

    public void setParameters(Parameter[] parameters) {
        this.parameters = parameters;
    }

    public Class<?> getReturnType() {
        return returnType;
    }

    public void setReturnType(Class<?> returnType) {
        this.returnType = returnType;
    }

    public Object getBean() {
        return bean;
    }

    public void setBean(Object bean) {
        this.bean = bean;
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

    public Object[] getParams() {
        return params;
    }

    public void setParams(Object[] params) {
        this.params = params;
    }
}
