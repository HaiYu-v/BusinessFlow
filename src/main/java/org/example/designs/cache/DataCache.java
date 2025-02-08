package org.example.designs.cache;

import java.util.HashMap;
import java.util.Map;

/**
 * 简易的数据缓存
 *
 * <p>
 *     TODO
 * </p>
 *
 * @author HaiYu
 * @version 1.0.0
 * @date 2025-02-08
 */
public class DataCache implements IDataCache{
    //全局map
    private Map<String,Object> globalMap = new HashMap<>();
    //临时map
    private Map<String,Object> temporaryMap = new HashMap<>();

    @Override
    public Object getTemporary(String key) {
        Object value = temporaryMap.get(key);
        if(temporaryMap.containsKey(key)) temporaryMap.remove(key);
        return value;
    }

    @Override
    public Object putTemporary(String key, Object value) {
        return temporaryMap.put(key, value);
    }

    @Override
    public void clearTemporary() {
        temporaryMap.clear();
    }

    @Override
    public Object getGlobal(String key) {
        return globalMap.get(key);
    }

    @Override
    public Object putGlobal(String key, Object value) {
        return globalMap.put(key, value);
    }

    @Override
    public void clearGlobal() {
        globalMap.clear();
    }

    @Override
    public void clear() {
        temporaryMap.clear();
        globalMap.clear();
    }

    @Override
    public boolean contains(String key) {
        if(temporaryMap.containsKey(key)) return true;
        if(globalMap.containsKey(key)) return true;
        return false;
    }
}
