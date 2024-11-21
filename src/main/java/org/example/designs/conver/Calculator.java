package org.example.designs.conver;

import java.util.HashMap;

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
    public static Object compute(HashMap<String,Object> variates,String formula){
        return compute(variates,formula);
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
    public static <T> T compute(HashMap<String,Object> variates,String formula,Class<T> retType){
        return (T) compute(variates,formula);
    }
}
