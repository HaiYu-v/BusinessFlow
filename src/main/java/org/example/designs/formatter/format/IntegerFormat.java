package org.example.designs.formatter.format;

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
 * @version 1.0.0
 * @date 2024-12-02
 */
public class IntegerFormat implements IFormat<Object, Integer> {

//    //可以为null
//    private boolean butNull = false;
    //支持字符串
    private boolean unString = false;
    //支持浮点数
    private boolean unDouble = false;
    //直接取整（默认）
    //四舍五入
    private boolean isRound = false;
    //向上取整
    private boolean isCeil = false;
    //这是负数
    private boolean toNegative = false;
    //这是自然数
    private boolean isNatural = false;
    //这是质数
    private boolean isPrime = false;
    //最大值
    private Integer max = Integer.MAX_VALUE;
    //最小值
    private Integer min = Integer.MIN_VALUE;
    //默认值
    private Integer defaultValue = null;

    private IntegerFormat() {
    }

    @Override
    public Integer format(Object data) {

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
        if(null == ret || ret > max || ret < min){
            return null;
        }
        return ret;
    }

    private Integer formatInt(Integer data) {
        if(isPrime) return NumberUtil.isPrime(data)? data : null;
        if(isNatural) return data >= 0 ? data : null;
        if(toNegative) return data >= 0 ? -data : data;
        return data;
    }

    private Integer formatDouble(Double data) {
        if(unDouble) return null;

        if(isCeil){
            return formatInt((int)Math.ceil(data));
        }else if(isRound){
            return formatInt((int)Math.round(data));
        }else {
            return formatInt(data.intValue());
        }
    }

    private Integer formatString(String data) {
        if(unString) return null;

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

    private Integer formatBigDecimal(BigDecimal data) {
        return formatDouble(data.doubleValue());
    }

    private Integer formatLong(Long data) {
        return formatInt(data.intValue());
    }
    private Integer formatFloat(Float data) {
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

    public IntegerFormat isNatural(){
        this.isNatural = true;
        return this;
    }

    public IntegerFormat isPrime(){
        this.isPrime = true;
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

}
