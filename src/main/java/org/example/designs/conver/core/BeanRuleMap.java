package org.example.designs.conver.core;

import org.example.designs.conver.rule.FieldRuleMap;

import java.util.HashMap;
import java.util.Map;

/**
 * Bean转换规则
 *
 * <p>
 *     一个Bean匹配一个
 * </p>
 *
 * @author HaiYu
 * @version 1.0.0
 * @date 2024-11-21
 */
public class BeanRuleMap {
    private Map<String, FieldRuleMap> beanRules = new HashMap<>();

    public FieldRuleMap get(String key){
        return beanRules.get(key);
    }

    public FieldRuleMap put (String key, FieldRuleMap fieldRuleMap){
        return beanRules.put(key, fieldRuleMap);
    }
}
