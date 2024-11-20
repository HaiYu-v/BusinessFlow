package org.example.designs.chain.context;

import org.example.designs.utils.AppContextUtil;


/**
 * Spring的Bean容器
 *
 * <p>
 *     TODO
 * </p>
 *
 * @author HaiYu
 * @version 1.0.0
 * @create 2024-11-19
 */
public class SpringBeanContext implements IContext{


    @Override
    public Object getBean(String var1) throws BeanException {
        return AppContextUtil.getBean(var1);
    }

    @Override
    public <T> T getBean(Class<T> var1) throws BeanException {
        return AppContextUtil.getBean(var1);
    }

    @Override
    public boolean containsBean(String var1) {
        return AppContextUtil.containsBean(var1);
    }
}
