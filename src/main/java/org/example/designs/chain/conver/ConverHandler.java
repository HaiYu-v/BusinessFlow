package org.example.designs.chain.conver;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.example.designs.Test1;
import org.example.designs.chain.desc.SourceDesc;
import org.example.designs.utils.MyReflectUtil;
import org.springframework.util.ObjectUtils;
import springfox.documentation.schema.Entry;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.fasterxml.jackson.databind.type.LogicalType.Map;

/**
 * 转换处理者
 *
 * <p>
 *     将源数据转换为目标数据
 * </p>
 *
 * @author HaiYu
 * @version 1.0.0
 * @date 2024-11-20
 */
public class ConverHandler {

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * 转换
     *
     * @param target 目标bean
     * @param rules  转换规则
     * @return
     */
    public static <T> T conver(T target, ConverRule rules) throws IllegalAccessException, NoSuchFieldException {

        List<Field> fields = MyReflectUtil.getFieldsWithGetterAndSetter(target.getClass());
        for(Field field:fields){
            // 设置字段可访问
            field.setAccessible(true);
            // 修改字段值
            field.set(target, rules.getValue(field,field.getType()));
        }
        return target;
    }

    public static void analysisRule(String rule) throws NoSuchFieldException{
        JSONObject jsonObject = JSONUtil.parseObj(rule);
        String targetCode = jsonObject.getStr("targetCode");
        Test1 target = new Test1();

        JSONObject rules = jsonObject.getJSONObject("rules");
        ConverRule converRule = new ConverRule();
        HashMap<String,String> curMap = (HashMap<String,String>) JSONUtil.toBean(rules, HashMap.class);
        for(Map.Entry<String,String> entry:curMap.entrySet()){
            Field field = target.getClass().getField(entry.getKey());
            SourceDesc sourceDesc = JSONUtil.toBean(entry.getValue(), SourceDesc.class);
            converRule.addRule(field,sourceDesc);
        }
        System.out.println(converRule.getValueMap().toString());
    }
}
