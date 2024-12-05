package org.example.designs.formatter.format;

import org.example.designs.formatter.FormatException;
import org.example.designs.formatter.util.NumberUtil;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 整数格式器
 *
 * <p>
 *     1.支持整数，浮点数，百分比和字符串
 * </p>
 *
 * @author HaiYu
 * @version 1.0.0
 * @date 2024-12-02
 */
public class IntegerFormat extends NumberFormat implements IFormat<Object, Integer> {

    //这是自然数
    private boolean toNatural = false;
    //这是质数
    private boolean toPrime = false;
    //支持浮点数
    private boolean unDouble = false;
    //最大值
    private Integer max = Integer.MAX_VALUE;
    //最小值
    private Integer min = Integer.MIN_VALUE;
    //默认值
    private Integer defaultValue = null;
    //直接取整（默认）
    //还支持四舍五入和向上取整
    protected RoundingMode integerRoundMode = RoundingMode.FLOOR;

    //保留位数
    protected int integerScale = -1;

    @Override
    public Integer format(Object data) throws FormatException {
        Integer ret = defaultValue;

        if(data instanceof Integer){
            ret = formatInt((Integer)data);
        }else if(data instanceof Long){
            ret = formatLong((Long) data);
        }else if(data instanceof Float){
            return formatFloat((Float) data);
        }else if(data instanceof Double){
            ret = formatDouble((Double) data);
        }else if(data instanceof BigDecimal){
            ret = formatBigDecimal((BigDecimal) data);
        }else if(data instanceof String){
            String str = (String) data;
            if(!str.isEmpty()) ret = formatString(str);
        }
        if(null == ret && !butNull) throw new FormatException("数据为null且无默认值");
        if(null == ret) return null;
        if(ret > max) throw new FormatException(String.format("[%d]超过最大值[%d]",ret,max));
        if(ret < min) throw new FormatException(String.format("[%d]超过最小值[%d]",ret,min));
        if(digit>10 || digit<0){
            throw new FormatException(String.format("位数[%d]超过int范围",digit));
        }
        if(NumberUtil.getDigitCount(ret.longValue()) > digit) throw new FormatException(String.format("[%d]的位数超过[%d]",ret,digit));
        return ret;
    }

    private Integer formatInt(Integer data) throws FormatException {
        data = NumberUtil.IntegerRound(data,integerScale, integerRoundMode);
        if(toNegative && toPositive) throw new FormatException(String.format("[%d]不能既是正数，又是负数",data));
        if(toNegative && toPrime) throw new FormatException(String.format("[%d]不能既是质数，又是负数",data));
        if(toNegative && toNatural) throw new FormatException(String.format("[%d]不能既是自然数，又是负数",data));
        if(toPrime && !NumberUtil.isPrime(data)) throw new FormatException(String.format("[%d]不是质数",data));
        if(toNatural && data < 0) throw new FormatException(String.format("[%d]不是自然数",data));
        if(toNegative) return data >= 0 ? -data : data;
        if(toPositive) return data < 0 ? -data: data;
        return data;
    }

    private Integer formatDouble(Double data) throws FormatException {
        if(unDouble) throw new FormatException(String.format("data[%f]不能是浮点数",data));
        if(data > Integer.MAX_VALUE || data < Integer.MIN_VALUE){
            throw new FormatException(String.format("[%f]超过int范围",data));
        }
        return formatInt(data.intValue());
    }

    private Integer formatString(String data) throws FormatException {
        if(unString) throw new FormatException(String.format("data[%s]不能是字符串",data));

        //浮点数
        if(NumberUtil.isFloatingPoint(data)){
            return formatDouble(Double.parseDouble(data));
        }
        //百分比
        if(NumberUtil.isPercentage(data)){
            return formatDouble(NumberUtil.parsePercentageWithNumberFormat(data));
        }

        if(NumberUtil.isInteger(data)){
            Long longInt = Long.parseLong(data);
            if(longInt > Integer.MAX_VALUE || longInt < Integer.MIN_VALUE){
                throw new FormatException(String.format("[%s]超过int范围",data));
            }
            return formatInt(Integer.parseInt(data));
        }
        throw new FormatException(String.format("[%s]不是浮点数，不是百分比，也不是Integer",data));
    }

    private Integer formatBigDecimal(BigDecimal data) throws FormatException {
        return formatDouble(data.doubleValue());
    }

    private Integer formatLong(Long data) throws FormatException {
        return formatInt(data.intValue());
    }
    private Integer formatFloat(Float data) throws FormatException {
        return formatDouble(data.doubleValue());
    }



    public static IntegerFormat build() {
        return new IntegerFormat();
    }


    public IntegerFormat unString(){
        this.unString = true;
        return this;
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

    public IntegerFormat toNegative(){
        this.toNegative = true;
        return this;
    }

    public IntegerFormat toNatural(){
        this.toNatural = true;
        return this;
    }

    public IntegerFormat toPrime(){
        this.toPrime = true;
        return this;
    }


    public IntegerFormat max(Integer max){
        this.max = max;
        return this;
    }

    public IntegerFormat min(Integer min){
        this.min = min;
        return this;
    }

    public IntegerFormat defaultValue(Integer defaultValue){
        this.defaultValue = defaultValue;
        return this;
    }

    public IntegerFormat butNull(){
        this.butNull = true;
        return this;
    }



    public IntegerFormat toPositive(){
        this.toPositive = true;
        return this;
    }

    public IntegerFormat isRound(){
        this.integerRoundMode = RoundingMode.HALF_UP;
        return this;
    }

    public IntegerFormat isCeil(){
        this.integerRoundMode = RoundingMode.CEILING;
        return this;
    }

    public IntegerFormat isRound(int scale){
        this.integerScale = scale;
        this.integerRoundMode = RoundingMode.HALF_UP;
        return this;
    }

    public IntegerFormat isCeil(int scale){
        this.integerScale = scale;
        this.integerRoundMode = RoundingMode.CEILING;
        return this;
    }

    public IntegerFormat scale(int scale){
        this.integerScale = scale;
        return this;
    }

    public IntegerFormat digit(int digit){
        this.digit = digit;
        return this;
    }

}
