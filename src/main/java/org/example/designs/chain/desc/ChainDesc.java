package org.example.designs.chain.desc;

import cn.hutool.core.util.ReflectUtil;
import org.example.designs.chain.annotation.Chain;
import org.example.designs.chain.context.BeanException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

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
 public class ChainDesc<T> {
    //业务处理者
    private T bean;
    //处理方法
    private Method method;
    //方法code
    private String methodCode;
    //方法描述
    private String desc;
    //参数类型列表
    private Class<?>[] paramTypes;
    //方法返回类型
    private Class<?> returnType;


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * 构造方法
     *
     * @param bean 业务点处理者
     * @param method 业务点处理方法
     * @param methodCode 方法编码
     * @param desc 描述信息
     */
    public ChainDesc(T bean, Method method, String methodCode, String desc) {
        this.bean = bean;
        this.method = method;
        this.methodCode = methodCode;
        this.desc = desc;
        this.returnType = method.getReturnType();
        this.paramTypes = method.getParameterTypes();
    }

    /** ----------------------------------------------------------------------------------------------------------------
     * 通过注解获取信息
     *
     * @param  bean   业务点处理者
     * @param  method 业务点处理方法
     */
     public ChainDesc(T bean, Method method){
        this.bean = bean;
        this.method = method;
        this.methodCode = null;
        this.desc = "";
        this.returnType = method.getReturnType();
        this.paramTypes = method.getParameterTypes();
        Chain chain = method.getAnnotation(Chain.class);
        if (null != chain){
            this.methodCode = chain.value();
            this.desc = chain.desc();
        }
    }


    /** ---------------------------------------------------------------------------------------------------------------------
     * 获取一个ChainDesc
     *
     * @param  bean   业务点处理者
     * @param  method 业务点处理方法
     * @return ChainDesc 业务点描述
     */
    public static <T> ChainDesc getDesc(T bean, Method method){
         return new ChainDesc(bean,method);
    }

    /** ---------------------------------------------------------------------------------------------------------------------
     * 获取一个ChainDesc
     *
     * @param  bean   业务点处理者
     * @param  methodCode 业务点处理方法编码
     * @return ChainDesc 业务点描述
     */
    public static <T> ChainDesc getDesc(T bean, String methodCode){
        return getDesc(bean,getMethod(bean.getClass(),methodCode));
    }

    /** ---------------------------------------------------------------------------------------------------------------------
     * 通过反射获取业务点描述
     *
     * 优先获取{@Chain}注解过的方法，如果没有注解，则取第一个方法名匹配的方法
     *
     * @param  beanType bean类型
     * @param  method     方法名
     * @return Method   方法
     */
    public static <T> Method getMethod(Class<T> beanType,String method) {
        Method[] methods = ReflectUtil.getPublicMethods(beanType);
        Method ret = null;
        for (Method cur : methods) {
            if (cur.isAnnotationPresent(Chain.class)) {
                // 获取 MyAnnotation 注解实例
                Chain chain = cur.getAnnotation(Chain.class);
                // 判断注解的 value 属性是否为 func
                if (cur.equals(chain.value())) {
                    return cur;
                }
                if(null == ret && cur.getName().equals(cur)) {
                    ret = cur;
                }
            }
        }
        return ret;
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * 执行对象的指定方法
     *
     * @param params 参数列表
     * @param returnType 返回类型
     * @return 返回类型
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @throws BeanException
     */
    public <R> R invoke(Object[] params,Class<R> returnType) throws InvocationTargetException, IllegalAccessException, BeanException {
        if(null == bean || null == method){
            throw new BeanException("bean or method is null");
        }
        return (R) method.invoke(bean,params);
    }


    public Class<?>[] getParamTypes() {
        return paramTypes;
    }

    public void setParamTypes(Class<?>[] paramTypes) {
        this.paramTypes = paramTypes;
    }

    public Class<?> getReturnType() {
        return returnType;
    }

    public void setReturnType(Class<?> returnType) {
        this.returnType = returnType;
    }

    public T getBean() {
        return bean;
    }

    public void setBean(T bean) {
        this.bean = bean;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public String getMethodCode() {
        return methodCode;
    }

    public void setMethodCode(String methodCode) {
        this.methodCode = methodCode;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
