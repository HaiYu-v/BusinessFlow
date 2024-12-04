package org.example.designs.formatter.format;

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
public class NumberFormat<R> {

    //可以为null
    protected boolean butNull = false;
    //支持字符串
    protected boolean unString = false;


    //直接取整（默认）
    //四舍五入
    protected boolean isRound = false;
    //向上取整
    protected boolean isCeil = false;
    //这是负数
    protected boolean toNegative = false;
    //这是正数
    protected boolean toPositive = false;
    //默认值
    protected R defaultValue = null;
}
