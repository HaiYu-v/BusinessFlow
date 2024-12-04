package org.example.designs.formatter.format;

import org.example.designs.formatter.FormatException;
import org.example.designs.formatter.util.NumberUtil;

import java.math.BigDecimal;

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
public class IntegerFormat extends NumberFormat<Integer> implements IFormat<Object, Integer> {

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
    //保留位数(默认十位)
    private int scale = 10;

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
        return ret;
    }

    private Integer formatInt(Integer data) throws FormatException {
        data = NumberUtil.IntegerRound(data,scale);
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

        if(isCeil){
            return formatInt((int)Math.ceil(data));
        }else if(isRound){
            return formatInt((int)Math.round(data));
        }else {
            return formatInt(data.intValue());
        }
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
            return formatInt(Integer.parseInt(data));
        }

        return null;
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

    public IntegerFormat isRound(){
        this.isRound = true;
        this.isCeil = false;
        return this;
    }

    public IntegerFormat isCeil(){
        this.isRound = false;
        this.isCeil = true;
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

    public IntegerFormat scale(int scale){
        this.scale = scale;
        return this;
    }

    public IntegerFormat toPositive(){
        this.toPositive = true;
        return this;
    }

}
