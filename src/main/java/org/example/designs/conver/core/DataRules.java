package org.example.designs.conver.core;

import org.example.designs.conver.rule.FieldRules;

import java.util.HashMap;
import java.util.Map;

/**
 * Data转换规则
 *
 * <p>
 *     一个Data匹配一个
 * </p>
 *
 * @author HaiYu
 * @version 1.0.0
 * @date 2024-11-21
 */
public class DataRules {
    private Map<String, FieldRules> dataRules = new HashMap<>();

    public FieldRules get(String key){
        return dataRules.get(key);
    }

    public FieldRules put (String key, FieldRules fieldRules){
        return dataRules.put(key, fieldRules);
    }
}
