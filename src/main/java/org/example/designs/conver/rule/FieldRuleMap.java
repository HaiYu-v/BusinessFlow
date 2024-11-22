package org.example.designs.conver.rule;

import org.example.designs.conver.desc.ConverDesc;

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
public class FieldRuleMap {
    //target.field:ConverDesc
    private Map<String, ConverDesc> valueMap = new HashMap<>();

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * TODO
     *
     * @param field
     * @param converDesc
     * @return {@link ConverDesc }
     */
    public ConverDesc put(String field, ConverDesc converDesc){
        return valueMap.put(field, converDesc);
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * TODO
     *
     * @param field
     * @return {@link ConverDesc }
     */
    public ConverDesc get(String field){
        return valueMap.get(field);
    }

    public Map<String, ConverDesc> getValueMap() {
        return valueMap;
    }

    public void setValueMap(Map<String, ConverDesc> valueMap) {
        this.valueMap = valueMap;
    }
}
