package org.example.designs.formatter.util;

import java.text.NumberFormat;
import java.text.ParseException;

/**
 * TODO
 *
 * <p>
 *     TODO
 * </p>
 *
 * @author HaiYu
 * @version 1.0.0
 * @date 2024-12-03
 */
public class TypeUtil {
    //String -> Integer
    public static Integer toInt(String data){
        if(isPercentage(data)){
            Double n = parsePercentageWithNumberFormat(data);
            return (null == n ? null : n.intValue());
        }
        return Integer.valueOf(data);
    }

    //Number -> Integer
    public static Integer toInt(Number data){
        return data.intValue();
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * 是不是百分比
     *
     * @param str
     * @return {@link Boolean }
     */
    public static Boolean isPercentage(String str){
        if (str == null || str.isEmpty()) {
            return false;
        }
        return str.matches("\\d+(\\.\\d+)?%");
    }

    public static Double parsePercentageWithNumberFormat(String percentageStr) {
        try {
            // 创建百分比解析器
            NumberFormat percentFormat = NumberFormat.getPercentInstance();

            // 将字符串解析为Number，再转换为Double
            Number number = percentFormat.parse(percentageStr);
            return number.doubleValue();
        } catch (ParseException e) {
            // 处理解析错误
            return null;
        }
    }
}
