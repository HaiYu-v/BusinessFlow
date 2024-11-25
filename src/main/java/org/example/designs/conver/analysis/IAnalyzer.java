package org.example.designs.conver.analysis;

import org.example.designs.conver.core.ConverException;
import org.example.designs.conver.core.DataRules;
import org.example.designs.conver.rule.FieldRules;

/**
 * 解析器接口
 *
 * <p>
 *     自定义解析器，用于解析成规则
 * </p>
 *
 * @author HaiYu
 * @version 1.0.0
 * @date 2024-11-22
 */
public interface IAnalyzer {
    /**
     * -----------------------------------------------------------------------------------------------------------------
     * 解析rule
     *
     * @param rules 要解析的Rules
     * @param dataRules 解析入dataRules
     * @return {@link DataRules }
     */
    DataRules analysis(Object rules,DataRules dataRules) throws ConverException;
}
