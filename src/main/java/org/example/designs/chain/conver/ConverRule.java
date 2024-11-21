package org.example.designs.chain.conver;

import org.example.designs.chain.desc.SourceDesc;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * 转换规则
 *
 * <p>
 *     TODO
 * </p>
 *
 * @author HaiYu
 * @version 1.0.0
 * @date 2024-11-20
 */
public class ConverRule {
    //target.field -- source.value
    private Map<Field, SourceDesc> valueMap = new HashMap<>();

    public <T> T getValue(Field field,Class<T> fieldType) throws NoSuchFieldException, IllegalAccessException {
        return (T)valueMap.get(field).getTargetValue();
    }


    public Map<Field, SourceDesc> getValueMap() {
        return valueMap;
    }

    public void setValueMap(Map<Field, SourceDesc> valueMap) {
        this.valueMap = valueMap;

    }

    public SourceDesc addRule(Field field, SourceDesc sourceDesc){
        return valueMap.put(field,sourceDesc);
    }
}
