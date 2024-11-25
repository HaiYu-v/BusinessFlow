package org.example.designs.conver.analysis;

import org.example.designs.conver.converter.ConverType;
import org.example.designs.conver.desc.ConverDesc;
import org.example.designs.conver.desc.SourceDesc;
import org.example.designs.conver.rule.FieldRules;

import java.util.List;
import java.util.Map;

/**
 * 解析工具
 *
 * <p>
 *     为了避免解析器的逻辑太过于混乱，
 *     所以用此工具来辅助，创造出符合要求的解析器
 * </p>
 *
 * @author HaiYu
 * @version 1.0.0
 * @date 2024-11-22
 */
public class AnalysisUtil {
    /**
     * -----------------------------------------------------------------------------------------------------------------
     * 构建SourceDesc
     *
     * @param code
     * @param field
     * @return {@link SourceDesc }
     */
    public static SourceDesc buildSourceDesc(String code, String field) {
        return new SourceDesc(code,field);
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * 构建ConverDesc
     *
     * @param type
     * @param sourceDesc
     * @param formula
     * @return {@link ConverDesc }
     */
    public static ConverDesc buildConverDesc(ConverType type, List<SourceDesc> sourceDesc, String formula){
        return new ConverDesc(type,sourceDesc,formula);
    }
    public static ConverDesc buildConverDesc(ConverType type, SourceDesc sourceDesc, String formula){
        return new ConverDesc(type,sourceDesc,formula);
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * FieldRules
     *
     * @param fieldMap
     * @return {@link FieldRules }
     */
    public static FieldRules buildFieldRules(Map<String, ConverDesc> fieldMap){
        FieldRules fieldRules = new FieldRules();
        for (Map.Entry<String, ConverDesc> entry : fieldMap.entrySet()) {
            fieldRules.put(entry.getKey(),entry.getValue());
        }
        return fieldRules;
    }
}
