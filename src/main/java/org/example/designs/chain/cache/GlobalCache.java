package org.example.designs.chain.cache;

import java.util.HashMap;
import java.util.Map;

/**
 * 业务流数据全局缓存
 *
 * <p>
 *     数据在整个业务流中共享，
 * </p>
 *
 * @author HaiYu
 * @version 1.0.0
 * @date 2024-11-21
 */
public class GlobalCache {
    //全局map
    private Map<String,Object> globalMap = new HashMap<>();

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * 缓存
     *
     * @param key
     * @param value
     * @return {@link Object }
     */
    public Object put(String key, Object value) {
        return globalMap.put(key,value);
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * 获取
     *
     * @param key
     * @return {@link Object }
     */
    public Object get(String key) {
        return globalMap.get(key);
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * 获取指定类型
     *
     * @param key
     * @param classType
     * @return {@link T }
     */
    public <T> T get(String key,Class<T> classType){
        return (T) globalMap.get(key);
    }

}