package org.example.designs.conver;

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
public class BeanRule {
    private Map<String,FieldRule> beanRules = new HashMap<>();

    public FieldRule get(String key){
        return beanRules.get(key);
    }

    public FieldRule put (String key, FieldRule fieldRule){
        return beanRules.put(key,fieldRule);
    }
}
