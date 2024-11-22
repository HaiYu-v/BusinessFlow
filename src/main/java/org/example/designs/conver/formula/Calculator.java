package org.example.designs.conver.formula;

import org.example.designs.conver.core.IDataSource;
import org.example.designs.conver.desc.SourceDesc;

import java.util.Map;

/**
 * 计算器
 *
 * <p>
 *     用来进行公式运算
 * </p>
 *
 * @author HaiYu
 * @version 1.0.0
 * @date 2024-11-21
 */
public class Calculator {
    /**
     * -----------------------------------------------------------------------------------------------------------------
     * 计算
     *
     * @param variates 变量列表
     * @param formula  计算公式
     * @return {@link Object }
     */
    public static Object compute(Map<String, SourceDesc> variates, String formula, IDataSource dataSource){
        return null;
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * 计算
     *
     * @param variates 变量列表
     * @param formula  计算公式
     * @param retType  返回类型
     * @return {@link Object }
     */
    public static <T> T compute(Map<String,SourceDesc> variates,String formula,Class<T> retType,IDataSource dataSource){
        return (T) compute(variates,formula,dataSource);
    }
}
