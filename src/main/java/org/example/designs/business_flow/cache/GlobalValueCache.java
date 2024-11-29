package org.example.designs.business_flow.cache;

import org.example.designs.conver.core.IDataSource;

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
public class GlobalValueCache implements IDataSource {
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
    @Override
    public Object put(String key, Object value) {
        return globalMap.put(key,value);
    }

    public void putAll(Map<String,Object> map){
        globalMap.putAll(map);
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * 是否有
     *
     * @param code
     * @return boolean
     */
    @Override
    public boolean contains(String code) {
        return globalMap.containsKey(code);
    }

    @Override
    public Map<String, Object> getMap() {
        return globalMap;
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * 获取
     *
     * @param key
     * @return {@link Object }
     */
    @Override
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
