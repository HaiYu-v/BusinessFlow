package org.example.designs.formatter.format.number;

import org.example.designs.formatter.FormatException;
import org.example.designs.formatter.util.NumberUtil;

import java.math.BigDecimal;

/**
 * 长整数格式器
 *
 * <p>
 *     - 约束条件
 *      - 这是自然数
 *      - 这是质数
 *      - 不支持浮点数
 * </p>
 *
 * @author HaiYu
 * @date 2024-12-02@version 1.0.0
 */
public class LongFormat extends NumberFormat<LongFormat,Long> {
    //这是自然数
    private boolean isNatural = false;
    //这是质数
    private boolean isPrime = false;
    //不支持浮点数
    private boolean unDouble = false;


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * 私有构造
     */
    private LongFormat(){

    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * build方法
     *
     * @return {@link IntegerFormat }
     */
    public static LongFormat build() {
        return new LongFormat()
                .max(Long.MAX_VALUE)
                .min(Long.MIN_VALUE)
                .digit(19)
                .scale(19);
    }

    @Override
    protected Long formatLong(Long data) throws FormatException {
        //判断位数
        if(digit>19 || digit<0){
            throw new FormatException(String.format("位数[%d]超过long范围",digit));
        }

        //负数跟其它数的冲突
        if(toNegative){
            if(toPositive)
                throw new FormatException(String.format("[%d]不能既是正数，又是负数",data));
            if(isPrime)
                throw new FormatException(String.format("[%d]不能既是质数，又是负数",data));
            if(isNatural)
                throw new FormatException(String.format("[%d]不能既是自然数，又是负数",data));
        }
        //整数取整
        data = NumberUtil.longRound(data, scale, roundMode);
        //转负数
        if(toNegative) data = (data >= 0 ? -data : data);
        //转正数
        if(toPositive) data = (data < 0 ? -data: data);
        //判断质数
        if(isPrime && !NumberUtil.isPrime(data))
            throw new FormatException(String.format("[%d]不是质数",data));
        //判断自然数
        if(isNatural && data < 0)
            throw new FormatException(String.format("[%d]不是自然数",data));
        //判断最大值
        if(data > max)
            throw new FormatException(String.format("[%d]超过最大值[%d]",data,max));
        //判断最小值
        if(data < min)
            throw new FormatException(String.format("[%d]超过最小值[%d]",data,min));

        return data;
    }

    @Override
    protected Long formatDouble(Double data) throws FormatException {
        if(unDouble) throw new FormatException(String.format("data[%f]不能是浮点数",data));
        if(data > Long.MAX_VALUE || data < Long.MIN_VALUE){
            throw new FormatException(String.format("[%f]超过long范围",data));
        }
        return formatLong(data.longValue());
    }
    @Override
    protected Long formatString(String data) throws FormatException {
        if(unString) throw new FormatException(String.format("data[%s]不能是字符串",data));

        //浮点数
        if(NumberUtil.isFloatingPoint(data)){
            return formatDouble(Double.parseDouble(data));
        }
        //百分比
        if(NumberUtil.isPercentage(data)){
            if(unPercent)
                throw new FormatException(String.format("data[%s]不能是百分比",data));
            return formatDouble(NumberUtil.parsePercentageWithNumberFormat(data));
        }

        //整数
        if(NumberUtil.isInteger(data,digit)){
            return formatLong(Long.parseLong(data));
        }
        throw new FormatException(String.format("[%s]不是浮点数，不是百分比，也不是Integer",data));
    }

    @Override
    protected Long formatBigDecimal(BigDecimal data) throws FormatException {
        return formatDouble(data.doubleValue());
    }

    @Override
    protected Long formatInt(Integer data) throws FormatException {
        return formatLong(data.longValue());
    }

    @Override
    protected Long formatFloat(Float data) throws FormatException {
        return formatDouble(data.doubleValue());
    }

    public LongFormat unDouble(){
        this.unDouble = true;
        return this;
    }

    public LongFormat unAll(){
        this.unString = true;
        this.unDouble = true;
        return this;
    }

    public LongFormat isNatural(){
        this.isNatural = true;
        return this;
    }

    public LongFormat isPrime(){
        this.isPrime = true;
        return this;
    }
}
