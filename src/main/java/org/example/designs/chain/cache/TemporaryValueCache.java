package org.example.designs.chain.cache;

import org.example.designs.conver.core.IDataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * 业务流数据临时缓存
 *
 * <p>
 *     数据只能取一次，取后数据就会销毁
 * </p>
 *
 * @author HaiYu
 * @version 1.0.0
 * @date 2024-11-21
 */
public class TemporaryValueCache implements IDataSource {
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
        temporaryMap.remove(key);
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
        temporaryMap.remove(key);
        return ret;
    }
}
