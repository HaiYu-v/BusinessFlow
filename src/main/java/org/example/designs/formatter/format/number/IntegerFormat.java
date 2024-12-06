package org.example.designs.formatter.format.number;

import org.example.designs.formatter.FormatException;
import org.example.designs.formatter.util.NumberUtil;

import java.math.BigDecimal;

/**
 * 整数格式器
 *
 * <p>
 *     TODO
 * </p>
 *
 * @author HaiYu
 * @date 2024-12-02@version 1.0.0
 */
public class IntegerFormat extends NumberFormat<IntegerFormat,Integer>{

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
    private IntegerFormat(){

    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * build方法
     *
     * @return {@link IntegerFormat }
     */
    public static IntegerFormat build() {
        return new IntegerFormat()
                .max(Integer.MAX_VALUE)
                .min(Integer.MIN_VALUE)
                .scale(10);
    }

    @Override
    protected Integer formatInt(Integer data) throws FormatException {
        //判断位数
        if(digit>10 || digit<0){
            throw new FormatException(String.format("位数[%d]超过int范围",digit));
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
        data = NumberUtil.IntegerRound(data, scale, roundMode);
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
    protected Integer formatDouble(Double data) throws FormatException {
        if(unDouble) throw new FormatException(String.format("data[%f]不能是浮点数",data));
        if(data > Integer.MAX_VALUE || data < Integer.MIN_VALUE){
            throw new FormatException(String.format("[%f]超过int范围",data));
        }
        return formatInt(data.intValue());
    }
    @Override
    protected Integer formatString(String data) throws FormatException {
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
        if(NumberUtil.isInteger(data)){
            Long longInt = Long.parseLong(data);
            if(longInt > Integer.MAX_VALUE || longInt < Integer.MIN_VALUE){
                throw new FormatException(String.format("[%s]超过int范围",data));
            }
            return formatInt(Integer.parseInt(data));
        }
        throw new FormatException(String.format("[%s]不是浮点数，不是百分比，也不是Integer",data));
    }

    @Override
    protected Integer formatBigDecimal(BigDecimal data) throws FormatException {
        return formatDouble(data.doubleValue());
    }

    @Override
    protected Integer formatLong(Long data) throws FormatException {
        return formatInt(data.intValue());
    }

    @Override
    protected Integer formatFloat(Float data) throws FormatException {
        return formatDouble(data.doubleValue());
    }

    public IntegerFormat unDouble(){
        this.unDouble = true;
        return this;
    }

    public IntegerFormat unAll(){
        this.unString = true;
        this.unDouble = true;
        return this;
    }

    public IntegerFormat isNatural(){
        this.isNatural = true;
        return this;
    }

    public IntegerFormat isPrime(){
        this.isPrime = true;
        return this;
    }


}
