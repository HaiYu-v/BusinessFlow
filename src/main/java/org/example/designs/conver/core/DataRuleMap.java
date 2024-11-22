package org.example.designs.conver.core;

import org.example.designs.conver.rule.FieldRuleMap;

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
public class DataRuleMap {
    private Map<String, FieldRuleMap> dataRules = new HashMap<>();

    public FieldRuleMap get(String key){
        return dataRules.get(key);
    }

    public FieldRuleMap put (String key, FieldRuleMap fieldRuleMap){
        return dataRules.put(key, fieldRuleMap);
    }
}
