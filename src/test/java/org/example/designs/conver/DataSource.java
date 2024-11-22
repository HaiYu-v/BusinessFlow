package org.example.designs.conver;

import org.example.designs.conver.core.IDataSource;

import java.util.HashMap;
import java.util.Map;

/**
 * TODO
 *
 * <p>
 *     TODO
 * </p>
 *
 * @author HaiYu
 * @version 1.0.0
 * @date 2024-11-21
 */
public class DataSource implements IDataSource {
    private Map<String,Object> dataMap = new HashMap<>();
    @Override
    public Object get(String code) {
        return dataMap.get(code);
    }

    @Override
    public Object put(String code, Object value) {
        return dataMap.put(code,value);
    }

    @Override
    public boolean contains(String code) {
        return dataMap.containsKey(code);
    }
}
