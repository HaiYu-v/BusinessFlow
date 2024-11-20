package org.example.designs.chain.context;

/**
 * Bean容器接口
 *
 * <p>
 *     TODO
 * </p>
 *
 * @author HaiYu
 * @version 1.0.0
 * @create 2024-11-19
 */
public interface IContext {
    Object getBean(String var1) throws BeanException;

    <T> T getBean(Class<T> var1) throws BeanException;

    boolean containsBean(String var1);
}
