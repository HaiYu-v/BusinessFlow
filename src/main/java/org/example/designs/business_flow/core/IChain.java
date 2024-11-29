package org.example.designs.business_flow.core;

import org.example.designs.business_flow.cache.GlobalValueCache;
import org.example.designs.business_flow.cache.TemporaryValueCache;

/**
 * 业务点接口
 *
 * <p>
 *     TODO
 * </p>
 *
 * @author HaiYu
 * @version 1.0.0
 * @create 2024-11-29
 */
public interface IChain {
    Object method(TemporaryValueCache temporaryCache, GlobalValueCache globalCache);
}
