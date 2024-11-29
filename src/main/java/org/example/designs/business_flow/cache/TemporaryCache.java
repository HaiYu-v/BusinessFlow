package org.example.designs.business_flow.cache;

import org.example.designs.conver.core.IDataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * 业务流数据临时缓存
 *
 * <p>
 *     数据只能传递到下一节点，下一节点会覆盖此数据
 * </p>
 *
 * @author HaiYu
 * @version 1.0.0
 * @date 2024-11-21
 */
public class TemporaryCache implements IDataSource {
    private Map<String,Object> temporaryMap = new HashMap<>();
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
        return temporaryMap.put(key,value);
    }

    public void putAll(Map<String,Object> map){
        temporaryMap.putAll(map);
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
        return temporaryMap.containsKey(code);
    }

    @Override
    public Map<String, Object> getMap() {
        return temporaryMap;
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * 获取, 用完即删
     *
     * @param key
     * @return {@link Object }
     */
    @Override
    public Object get(String key) {
        Object ret = temporaryMap.get(key);
        return ret;
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * 获取指定类型, 用完即删
     *
     * @param key
     * @param classType
     * @return {@link T }
     */
    public <T> T get(String key,Class<T> classType){
        T ret = (T) temporaryMap.get(key);
        return ret;
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * 清除
     */
    public void clear(){
        temporaryMap.clear();
    }


}
