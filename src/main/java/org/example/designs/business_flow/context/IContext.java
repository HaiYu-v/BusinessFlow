package org.example.designs.business_flow.context;

import org.example.designs.business_flow.core.BusinessFlowException;

/**
 * Data容器接口
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
    Object getData(String var1) throws BusinessFlowException;

    <T> T getData(Class<T> var1) throws BusinessFlowException;

    boolean containsData(String var1);
}
