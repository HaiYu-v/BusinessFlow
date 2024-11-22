package org.example.designs.business_flow.context;

import org.example.designs.business_flow.core.BusinessFlowException;
import org.example.designs.utils.AppContextUtil;


/**
 * Spring的Data容器
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
    public Object getData(String var1) throws BusinessFlowException {
        return AppContextUtil.getBean(var1);
    }

    @Override
    public <T> T getData(Class<T> var1) throws BusinessFlowException {
        return AppContextUtil.getBean(var1);
    }

    @Override
    public boolean containsData(String var1) {
        return AppContextUtil.containsBean(var1);
    }
}
