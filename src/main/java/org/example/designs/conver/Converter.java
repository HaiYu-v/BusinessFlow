package org.example.designs.conver;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.example.designs.conver.desc.SourceDesc;
import org.example.designs.utils.MyReflectUtil;


import java.lang.reflect.Field;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 转换器
 *
 * <p>
 *     将源数据转换为目标数据
 * </p>
 *
 * @author HaiYu
 * @version 1.0.0
 * @date 2024-11-20
 */
public class Converter {

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * 批量转换
     *
     * @param parameters
     * @param ruleCache
     * @param dataSource
     * @return 转换是否成功
     * @throws IllegalAccessException
     * @throws NoSuchFieldException
     */
    public static Object[] converBatch(Parameter[] parameters, BeanRule ruleCache, IDataSource dataSource) throws IllegalAccessException, NoSuchFieldException {
        Object[] params = new Object[parameters.length];
        for (int i = 0; i < parameters.length; i++) {
            FieldRule fieldRule = ruleCache.get(parameters[i].getName());
            conver(params[i],fieldRule,dataSource);
        }

        return params;
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * 转换
     *
     * @param target    目标bean
     * @param fieldRule 转换规则
     * @param dataSource
     * @return 转换是否成功
     * @throws IllegalAccessException
     * @throws NoSuchFieldException
     */
    public static boolean conver(Object target, FieldRule fieldRule, IDataSource dataSource) throws IllegalAccessException, NoSuchFieldException {
        List<Field> fields = MyReflectUtil.getFieldsWithGetterAndSetter(target.getClass());
        for(Field field:fields){
            // 设置字段可访问
            field.setAccessible(true);
            // 修改字段值
            field.set(target, fieldRule.getValue(field.getName(),field.getType(),dataSource));
        }
        return true;
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * 解析Rule的JSON字符串
     *
     * @param ruleJSON rule字符串
     * @param beanRuleCache bean转换规则缓存
     * @return {@link FieldRule }
     * @throws NoSuchFieldException
     */
    public static FieldRule analysis(String ruleJSON, BeanRule beanRuleCache) throws NoSuchFieldException{
        JSONObject rule = JSONUtil.parseObj(ruleJSON);
        //目标编码
        String targetCode = rule.getStr("targetCode");
        //转换规则
        JSONObject rules = rule.getJSONObject("rules");

        FieldRule fieldRule = new FieldRule();
        HashMap<String,String> curMap = (HashMap<String,String>) JSONUtil.toBean(rules, HashMap.class);
        for(Map.Entry<String,String> entry:curMap.entrySet()){
            SourceDesc sourceDesc = JSONUtil.toBean(entry.getValue(), SourceDesc.class);
            fieldRule.put(entry.getKey(),sourceDesc);
        }

        System.out.println(fieldRule.getValueMap().toString());
        return beanRuleCache.put(targetCode, fieldRule);
    }
}
