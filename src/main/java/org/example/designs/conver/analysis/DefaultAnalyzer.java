package org.example.designs.conver.analysis;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.example.designs.conver.converter.ConverType;
import org.example.designs.conver.core.ConverException;
import org.example.designs.conver.core.DataRules;
import org.example.designs.conver.desc.ConverDesc;
import org.example.designs.conver.desc.SourceDesc;
import org.example.designs.conver.rule.FieldRules;

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
    public DataRules analysis(Object rules, DataRules dataRules) throws ConverException {
        if(!(rules instanceof String)) {
            throw new ConverException("不支持该类型:"+rules.toString());
        }
        String json = (String) rules;
        JSONObject ruleJSON = JSONUtil.parseObj(json);
        //目标编码
        String targetCode = ruleJSON.getStr("targetCode");
        //转换规则
        JSONObject curRules = ruleJSON.getJSONObject("rules");

        //解析成字段转换规则
        HashMap<String,JSONObject> curMap = (HashMap<String,JSONObject>) JSONUtil.toBean(curRules, HashMap.class);
        Map<String,ConverDesc> fieldMap = new HashMap<>();
        //解析fieldRules
        for(Map.Entry<String,JSONObject> entry:curMap.entrySet()){
            String field = entry.getKey();
            JSONObject conver = entry.getValue();
            //解析SourceDesc
            List<SourceDesc> sourceDescList = AnalysisUtil.buildSourceDesc(conver.getStr("source"));
            //解析ConverDesc
            ConverDesc converDesc = AnalysisUtil.buildConverDesc(
                            ConverType.ValueOf(conver.getStr("type"))
                            ,sourceDescList,conver.getStr("formula"));
            fieldMap.put(field,converDesc);
        }
        FieldRules fieldRules = AnalysisUtil.buildFieldRules(fieldMap);
        //添加到dataRules中
        dataRules.put(targetCode, fieldRules);
        return dataRules;
    }
}
