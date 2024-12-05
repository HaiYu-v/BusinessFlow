package org.example.designs.formatter.format;

import java.math.RoundingMode;

/**
 * TODO
 *
 * <p>
 *     TODO
 * </p>
 *
 * @author HaiYu
 * @version 1.0.0
 * @date 2024-12-04
 */
public class NumberFormat {

    //可以为null
    protected boolean butNull = false;
    //支持字符串
    protected boolean unString = false;
    //这是负数
    protected boolean toNegative = false;
    //这是正数
    protected boolean toPositive = false;

    //整数位数（默认十位）
    protected int digit = 10;

}
