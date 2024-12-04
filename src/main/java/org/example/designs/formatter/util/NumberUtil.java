package org.example.designs.formatter.util;

import java.text.NumberFormat;
import java.text.ParseException;

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
     * 是不是浮点数
     *
     * @param str
     * @return {@link Boolean }
     */
    public static Boolean isFloatingPoint (String str){
        if (str == null || str.isEmpty()) {
            return false;
        }
        return str.matches("^[-+]?((\\d+\\.?\\d*)|(\\.\\d+))([eE][-+]?\\d+)?$");
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * 是不是整数
     *
     * @param str
     * @return boolean
     */
    public static boolean isInteger(String str) {
        if (str == null) return false;
        return str.matches("^[+-]?\\d+$");
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * 是不是质数
     *
     * @param number
     * @return boolean
     */
    public static boolean isPrime(Integer number) {

        if (number == null) {
            return false;
        }

        // 1和负数不是质数
        if (number <= 1) {
            return false;
        }

        // 2是质数
        if (number == 2) {
            return true;
        }

        // 偶数不是质数（除了2）
        if (number % 2 == 0) {
            return false;
        }

        // 只需要判断到平方根
        for (int i = 3; i <= Math.sqrt(number); i += 2) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }
}
