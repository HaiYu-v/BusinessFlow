package org.example.designs.conver.analysis;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.example.designs.conver.converter.ConverType;
import org.example.designs.conver.core.ConverException;
import org.example.designs.conver.core.DataRules;
import org.example.designs.conver.desc.ConverDesc;
import org.example.designs.conver.desc.SourceDesc;
import org.example.designs.conver.rule.FieldRules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 默认解析器
 *
 * <p>
 *     TODO
 * </p>
 *
 * @author HaiYu
 * @version 1.0.0
 * @date 2024-11-22
 */
public class DefaultAnalyzer implements IAnalyzer{

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * 解析
     *
     * @param rules 接收一个JSON
     * @param dataRules
     * @return {@link DataRules }
     */
    @Override
    public DataRules analysis(Object rules, DataRules dataRules) throws Exception {
        if(!(rules instanceof String)) {
            throw new ConverException("只能解析String类型的rule:"+rules.toString());
        }
        if(!JSONUtil.isTypeJSON((String) rules)){
            throw new ConverException("只能解析JSON格式的rule");
        }


        String json = (String) rules;
        JSONObject ruleJSON = null;
        try {
            ruleJSON = JSONUtil.parseObj(json);
        } catch (Exception e) {
            throw new ConverException("JSON格式错误(公式里的字符串用单引号[']表示)",e);
        }
        //目标编码
        String sourceCode = ruleJSON.getStr("sourceCode");
        //转换规则
        JSONObject curRules = ruleJSON.getJSONObject("rules");

        //解析成各字段的转换规则
        HashMap<String,JSONObject> curMap = null;
        try {
            curMap = (HashMap<String, JSONObject>) JSONUtil.toBean(curRules, HashMap.class);
        } catch (Exception e) {
            throw new ConverException("sourceCode["+sourceCode+"]的转换规则有错误",e);
        }
        Map<String,ConverDesc> fieldMap = new HashMap<>();

        //解析fieldRules
        for(Map.Entry<String,JSONObject> entry:curMap.entrySet()){
            String field = entry.getKey();
            JSONObject conver = entry.getValue();

            //解析SourceDesc
            List<String> list = null;
            try {
                list = JSONUtil.toList(conver.getStr("source"), String.class);
            } catch (Exception e) {
                throw new ConverException("field["+field+"]的source不是一个JSON数组",e);
            }

            List<SourceDesc> sourceDescList = new ArrayList<>();
            for (String s : list) {
                sourceDescList.add(AnalysisUtil.buildSourceDesc(s));
            }

            //解析ConverDesc
            ConverDesc converDesc = AnalysisUtil.buildConverDesc(
                            ConverType.ValueOf(conver.getStr("type"))
                            ,sourceDescList,conver.getStr("formula"));
            fieldMap.put(field,converDesc);
        }
        FieldRules fieldRules = AnalysisUtil.buildFieldRules(fieldMap);

        //添加到dataRules中
        dataRules.put(sourceCode, fieldRules);
        return dataRules;
    }
}
