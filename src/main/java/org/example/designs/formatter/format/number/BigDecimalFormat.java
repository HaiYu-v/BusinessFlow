package org.example.designs.formatter.format.number;

import org.example.designs.formatter.FormatException;
import org.example.designs.formatter.util.NumberUtil;

import java.math.BigDecimal;

/**
 * 精确小数的格式化
 *
 * <p>
 *     TODO
 * </p>
 * 
 * @date  2024-12-05 
 * @author  HaiYu
 * @version 1.0.0
 */
public class BigDecimalFormat extends NumberFormat<BigDecimalFormat, BigDecimal>{


    private BigDecimalFormat (){}

    public static BigDecimalFormat build(){
        return new BigDecimalFormat()
                .max(new BigDecimal(Long.MAX_VALUE))
                .min(new BigDecimal(Long.MIN_VALUE))
                .scale(15);
    }

    @Override
    protected BigDecimal formatBigDecimal(BigDecimal data) throws FormatException {
        //判断位数
        if(digit>10 || digit<0){
            throw new FormatException(String.format("位数[%f]超过double范围(只支持到int)",digit));
        }

        //负数跟其它数的冲突
        if(toNegative && toPositive)
            throw new FormatException(String.format("[%f]不能既是正数，又是负数",data));

        //整数取整
        data = data.setScale(scale, roundMode);
        //转负数
        if(toNegative) data = (data.compareTo(BigDecimal.ZERO) > 0? data.negate() : data);
        //转正数
        if(toPositive) data = (data.compareTo(BigDecimal.ZERO) < 0? data.negate() : data);

        //判断最大值
        if(data.compareTo(max) > 0)
            throw new FormatException(String.format("[%f]超过最大值[%f](支持到long)",data,max));
        //判断最小值
        if(data.compareTo(min) < 0)
            throw new FormatException(String.format("[%f]超过最小值[%f](支持到long)",data,min));

        return data;
    }

    @Override
    protected BigDecimal formatDouble(Double data) throws FormatException {
        if(BigDecimal.valueOf(data).toString().length()>17)
            throw new FormatException(String.format("Double只能保证16位数的精度[%s]",data));
        return formatBigDecimal(BigDecimal.valueOf(data));
    }

    @Override
    protected BigDecimal formatString(String data) throws FormatException {
        if(unString) throw new FormatException(String.format("data[%s]不能是字符串",data));

        //浮点数
        if(NumberUtil.isFloatingPoint(data)){
            return formatDouble(Double.valueOf(data));
        }

        //百分比
        if(NumberUtil.isPercentage(data)){
            if(unPercent)
                throw new FormatException(String.format("data[%s]不能是百分比",data));

            return formatDouble(Double.valueOf(NumberUtil.parsePercentageWithNumberFormat(data)));
        }

        //整数
        if(NumberUtil.isInteger(data)){
            return formatBigDecimal(new BigDecimal(data));
        }
        throw new FormatException(String.format("[%s]不是浮点数，不是百分比，也不是Integer",data));
    }

    @Override
    protected BigDecimal formatInt(Integer data) throws FormatException {
        return formatBigDecimal(BigDecimal.valueOf(data.doubleValue()));
    }

    @Override
    protected BigDecimal formatLong(Long data) throws FormatException {
        return formatBigDecimal(BigDecimal.valueOf(data.intValue()));
    }
    @Override
    protected BigDecimal formatFloat(Float data) throws FormatException {
        if(data.toString().length()>7)
            throw new FormatException(String.format("Float只能保证6位数的精度[%s]",data));
        return formatBigDecimal(new BigDecimal(data.toString()));
    }

    public BigDecimalFormat scale(int digit,int scale){
        this.digit = digit;
        this.scale = scale;
        return this;
    }

}
