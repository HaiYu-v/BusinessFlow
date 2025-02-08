package org.example.designs.cache;

import java.util.Map;

/**
 * 数据缓存接口
 *
 * <p>
 *     1.临时缓存，只存一次，使用后就立刻删除
 *     2.全局缓存，一直存在，直到手动删除
 * </p>
 *
 * @author HaiYu
 * @version 1.0.0
 * @create 2025-02-08
 */
public interface IDataCache {
    //获得临时缓存
    Object getTemporary(String key);
    //设置临时缓存
    Object putTemporary(String key,Object value);
    //清除临时缓存
    public void clearTemporary();
    //获得全局缓存
    Object getGlobal(String key);
    //获得全局缓存
    Object putGlobal(String key,Object value);
    //获得全局缓存
    public void clearGlobal();
    //清除所有缓存
    public void clear();
    //是否包含
    boolean contains(String key);
}
