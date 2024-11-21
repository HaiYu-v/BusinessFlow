package org.example.designs.conver;

import org.example.designs.conver.desc.SourceDesc;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * 字段转换规则
 *
 * <p>
 *     一个字段匹配一个转换规则
 * </p>
 *
 * @author HaiYu
 * @version 1.0.0
 * @date 2024-11-20
 */
public class FieldRule {
    //target.field:SourceDesc
    private Map<String, SourceDesc> valueMap = new HashMap<>();

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * 获取值，根据规则
     *
     * @param field
     * @param fieldType
     * @param dataSource
     * @return {@link T }
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    public <T> T getValue(String field, Class<T> fieldType, IDataSource dataSource) throws NoSuchFieldException, IllegalAccessException {
        return (T)valueMap.get(field).getTargetValue(dataSource);
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * TODO
     *
     * @param field
     * @param sourceDesc
     * @return {@link SourceDesc }
     */
    public SourceDesc put(String field, SourceDesc sourceDesc){
        return valueMap.put(field,sourceDesc);
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * TODO
     *
     * @param field
     * @return {@link SourceDesc }
     */
    public SourceDesc get(String field){
        return valueMap.get(field);
    }

    public Map<String, SourceDesc> getValueMap() {
        return valueMap;
    }

    public void setValueMap(Map<String, SourceDesc> valueMap) {
        this.valueMap = valueMap;
    }
}
