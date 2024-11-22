package org.example.designs.conver.core;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.example.designs.conver.desc.ConverDesc;
import org.example.designs.conver.rule.FieldRuleMap;
import org.example.designs.utils.MyReflectUtil;


import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 转换器
 *
 * <p>
 *     将源数据转换为目标数据，转换时需要提供数据源
 *     转换时再从数据源加载值，扩展性会更好一些
 *
 *     记录一个code和他的一个field
 *     如果field为空，则表示此code指向一个数值，而非Bean
 *     如果field不为空，则取bean的此field值
 * </p>
 *
 * @author HaiYu
 * @version 1.0.0
 * @date 2024-11-20
 */
public class Converter {


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * 转换
     *
     * @param  target      目标bean
     * @param  targetCode  目标bean的code
     * @param  beanRuleMap bean转换规则缓存
     * @param  dataSource  数据源
     * @return 转换是否成功
     * @throws Exception
     */
    public static boolean conver(Object target, String targetCode, BeanRuleMap beanRuleMap, IDataSource dataSource) throws Exception {
        FieldRuleMap fieldRuleMap = beanRuleMap.get(targetCode);
        if(null == fieldRuleMap){
            throw new ConverException("Bean转换规则Map里，没有["+targetCode+"]");
        }
        List<Field> fields = MyReflectUtil.getFieldsWithGetterAndSetter(target.getClass());
        for(Field field:fields){
            // 设置字段可访问
            field.setAccessible(true);
            // 修改字段值
            ConverDesc converDesc = fieldRuleMap.get(field.getName());
            field.set(target,converDesc.getConverValue(dataSource));
        }
        return true;
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * 解析规则JSON
     *
     * @param json rule字符串
     * @param beanRuleMapCache bean转换规则缓存
     * @return {@link FieldRuleMap }
     */
    public static boolean analysis(String json, BeanRuleMap beanRuleMapCache){
        JSONObject ruleJSON = JSONUtil.parseObj(json);
        //目标编码
        String targetCode = ruleJSON.getStr("targetCode");
        //转换规则
        JSONObject rules = ruleJSON.getJSONObject("rules");

        //解析成字段转换规则
        FieldRuleMap fieldRuleMap = new FieldRuleMap();
        HashMap<String,JSONObject> curMap = (HashMap<String,JSONObject>) JSONUtil.toBean(rules, HashMap.class);
        for(Map.Entry<String,JSONObject> entry:curMap.entrySet()){
            String field = entry.getKey();
            String conver = entry.getValue().toString();
            ConverDesc converDesc = ConverDesc.build(conver);
            fieldRuleMap.put(field, converDesc);
        }
        //解析成bean转换规则
        beanRuleMapCache.put(targetCode, fieldRuleMap);
        return true;
    }
}
