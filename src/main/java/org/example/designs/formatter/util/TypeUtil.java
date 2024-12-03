package org.example.designs.formatter.util;

import java.math.BigDecimal;
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
    private boolean isCeil = true;
    private boolean isRound = false;
    //String -> Integer
    public static Integer toInt(String data,boolean isCeil){
        if(isPercentage(data)){
            Double n = parsePercentageWithNumberFormat(data);
            return toInt(n,isCeil);
        }

        if(isFloatingPoint(data)){
            Double n = Double.valueOf(data);
            return toInt(n,isCeil);
        }
        return Integer.valueOf(data);
    }

    //Number -> Integer
    public static Integer toInt(Number data,boolean isCeil){
        int ret = data.intValue();

        if((data instanceof Double) || (data instanceof Float) || (data instanceof BigDecimal)){

        }
        return data.intValue();
    }

    public static Double toDouble(String data,boolean isCeil){

    }

    public static Double toDouble(Number data,boolean isCeil){

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

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * 百分比字符串转Double
     *
     * @param percentageStr
     * @return {@link Double }
     */
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

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * 浮点数字符串转Double
     *
     * @param str
     * @return {@link Boolean }
     */
    public static Boolean isFloatingPoint (String str){
        if (str == null || str.isEmpty()) {
            return false;
        }
        return str.matches("[+-]?(\\d+(\\.\\d*)?|\\.\\d+)([eE][+-]?\\d+)?");
    }
}
