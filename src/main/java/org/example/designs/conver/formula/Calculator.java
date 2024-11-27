package org.example.designs.conver.formula;

import org.example.designs.conver.core.ConverException;
import org.example.designs.conver.core.IDataSource;
import org.example.designs.conver.desc.SourceDesc;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.Map;

/**
 * 计算器
 *
 * <p>
 *     用来进行公式运算,使用的是SpEL
 *
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
    public static <T> T compute(Map<String, SourceDesc> variates, String formula, IDataSource dataSource,Class<T> type) throws ConverException {
        T ret = null;
        try {
            ExpressionParser parser = new SpelExpressionParser();
            StandardEvaluationContext context = new StandardEvaluationContext();
            //设置变量
            for (Map.Entry<String, SourceDesc> stringSourceDescEntry : variates.entrySet()) {
                context.setVariable(stringSourceDescEntry.getKey(),dataSource.get(stringSourceDescEntry.getValue().getKey()));
            }
            //公式计算
            ret = parser.parseExpression(formula).getValue(context,type);
        } catch (Exception e) {
            throw new ConverException("公式["+formula+"]计算失败，原因是："+e.getMessage());
        }
        return ret;
    }
}
