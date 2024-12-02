package org.example.designs.formatter.util;

import java.math.BigDecimal;

/**
 * 数据工具类
 *
 * <p>
 *     1.是什么数（整数，浮点数，负数，百分数）
 *     2.整数保留几位
 *     3.小数保留几位
 *     4.向上取值/四舍五入
 * </p>
 *
 * @author HaiYu
 * @version 1.0.0
 * @date 2024-12-02
 */
public class NumberUtil {
    public static <R extends Number> R valueOf(Object data, Class<R> retType){
        String str = null;
        R ret = null;
        if(data instanceof Number){
            if(data instanceof Integer) str = Integer.toString((Integer) data);
            if(data instanceof Long) str = Long.toString((Long) data);
            if(data instanceof Double) str = Double.toString((Double) data);
            if(data instanceof Float) str = Float.toString((Float) data);
            if(data instanceof BigDecimal) str = ((BigDecimal) data).toPlainString();
        }

        if(data instanceof String){
            str = (String) data;
        }

        if (null==str || str.equals("")){
            return null;
        }

        ret = parse(str, retType);

        return ret;
    }

    public static <R> R parse(String data, Class<R> type) {
        //Integer
        if(type.equals(Integer.class)){
            //处理百分比
            if(isPercentage(data)){
                data = data.substring(0,data.length()-1);
                return (R) (Integer.valueOf(Integer.parseInt(data)/100));
            }
            //处理浮点数
            if(isFloatingPoint(data)){
                data = data.split("\\.")[0];
            }

            return (R) Integer.valueOf(data);
        }

        //Long
        if(type.equals(Long.class)){
            //处理百分比
            if(isPercentage(data)){
                data = data.substring(0,data.length()-1);
                return (R) (Long.valueOf(Long.parseLong(data)/100));
            }
            //处理浮点数
            if(isFloatingPoint(data)){
                data = data.split("\\.")[0];
            }
            return (R) Long.valueOf(data);
        }

        //Double
        if(type.equals(Double.class)){
            //处理百分比
            if(data.charAt(data.length()-1) == '%'){
                data = data.substring(0,data.length()-1);
                return (R) (Double.valueOf(Double.parseDouble(data)/100.0));
            }
            //处理浮点数
            return (R) Double.valueOf(data);
        }

//        if(type.equals(BigDecimal.class)){
//            //处理百分比
//            if(isPercentage(data)){
//                data = data.substring(0,data.length()-1);
//                return (R) (Long.valueOf(new BigDecimal(data).divide(100)));
//            }
//            //处理浮点数
//            if(isFloatingPoint(data)){
//                data = data.split("\\.")[0];
//            }
//            return (R) Long.valueOf(data);
//        }

        return null;
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * 验证字符串是不是数字
     *
     * @param str
     * @return boolean
     */
    public static boolean isNumber(String str){
        if (str == null || str.isEmpty()) {
            return false;
        }
        return str.matches("\\d+");
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
     * 是不是浮点数
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
