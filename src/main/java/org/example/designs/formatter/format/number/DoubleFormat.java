package org.example.designs.formatter.format.number;

import org.example.designs.formatter.FormatException;
import org.example.designs.formatter.util.NumberUtil;

import java.math.BigDecimal;

/**
 * 浮点数格式器
 *
 * <p>
 *     - 约束条件
 *      - 大小只支持到Long
 *      - double只能保证16位数的精确（可以修改unPrecision来取消这个限制）
 *      - float 只能保证6位数的精确
 * </p>
 *
 * @author HaiYu
 * @version 1.0.0
 * @date 2024-12-03
 */
public class DoubleFormat extends NumberFormat<DoubleFormat,Double>{


    //不保证精度
    private boolean unPrecision = false;

    private DoubleFormat (){}

    public static DoubleFormat build(){
        return new DoubleFormat()
                .max(new Double(Long.MAX_VALUE))
                .min(new Double(Long.MIN_VALUE))
                .digit(19)
                .scale(16);
    }

    @Override
    protected Double formatBigDecimal(BigDecimal data) throws FormatException {
        //判断位数
        if(digit>19 || digit<0){
            throw new FormatException(String.format("位数[%f]超过double范围(只支持到long)",digit));
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
        if(data.compareTo(new BigDecimal(max)) > 0)
            throw new FormatException(String.format("[%f]超过最大值[%f](支持到long)",data,max));
        //判断最小值
        if(data.compareTo(new BigDecimal(min)) < 0)
            throw new FormatException(String.format("[%f]超过最小值[%f](支持到long)",data,min));

        return data.doubleValue();
    }

    @Override
    protected Double formatDouble(Double data) throws FormatException {
        if(BigDecimal.valueOf(data).toString().length()>17)
            if(!unPrecision) throw new FormatException(String.format("Double只能保证16位数的精度[%s]",data));
        return formatBigDecimal(BigDecimal.valueOf(data));
    }

    @Override
    protected Double formatString(String data) throws FormatException {
        if(unString) throw new FormatException(String.format("data[%s]不能是字符串",data));

        //浮点数
        if(NumberUtil.isFloatingPoint(data)){
            return formatBigDecimal(new BigDecimal(data));
        }

        //百分比
        if(NumberUtil.isPercentage(data)){
            if(unPercent)
                throw new FormatException(String.format("data[%s]不能是百分比",data));

            return formatBigDecimal(BigDecimal.valueOf(NumberUtil.parsePercentageWithNumberFormat(data)));
        }

        //整数
        if(NumberUtil.isInteger(data,digit)){
            return formatBigDecimal(new BigDecimal(data));
        }
        throw new FormatException(String.format("[%s]不是浮点数，不是百分比，也不是Integer",data));
    }

    @Override
    protected Double formatInt(Integer data) throws FormatException {
        return formatBigDecimal(BigDecimal.valueOf(data.doubleValue()));
    }

    @Override
    protected Double formatLong(Long data) throws FormatException {
        return formatBigDecimal(BigDecimal.valueOf(data.intValue()));
    }
    @Override
    protected Double formatFloat(Float data) throws FormatException {
        if(data.toString().length()>7)
            if(!unPrecision) throw new FormatException(String.format("Float只能保证6位数的精度[%s]",data));
        return formatBigDecimal(new BigDecimal(data.toString()));
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * 约束整数位数（只判断，不约数）和小数位数
     *
     * @param digit 整数位数
     * @param scale 小数位数
     * @return {@link DoubleFormat }
     */
    public DoubleFormat scale(int digit,int scale){
        this.digit = digit;
        this.scale = scale;
        return this;
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * 取消精度位数的限制
     *
     * @return {@link DoubleFormat }
     */
    public DoubleFormat unPrecision(){
        this.unPrecision = true;
        return this;
    }


}
