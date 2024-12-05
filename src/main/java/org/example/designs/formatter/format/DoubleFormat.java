package org.example.designs.formatter.format;

import org.example.designs.formatter.FormatException;
import org.example.designs.formatter.util.NumberUtil;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 浮点数格式器
 *
 * <p>
 *     TODO
 * </p>
 *
 * @author HaiYu
 * @version 1.0.0
 * @date 2024-12-03
 */
public class DoubleFormat extends NumberFormat implements IFormat<Object, Double>{

    //最大值
    private Double max = Double.MAX_VALUE;
    //最小值
    private Double min = Double.MIN_VALUE;
    //默认值
    private Double defaultValue = null;

    //直接取整（默认）
    //还支持四舍五入和向上取整
    protected RoundingMode decimalRoundMode = RoundingMode.FLOOR;

    //小数保留位数(默认十五位)
    protected int decimalScale = 15;

    @Override
    public Double format(Object data) throws FormatException {
        Double ret = defaultValue;
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
        if(ret > max) throw new FormatException(String.format("[%s]超过最大值[%s]",ret,max));
        if(ret < min) throw new FormatException(String.format("[%s]超过最小值[%s]",ret,min));
        if(digit>19 || digit<0){
            throw new FormatException(String.format("位数[%d]超过double(支持到long)范围",digit));
        }
        if(NumberUtil.getDigitCount(ret.longValue()) > digit) throw new FormatException(String.format("[%d]的位数超过[%d]",ret,digit));

        return ret;
    }

    private Double formatDouble(Double data) throws FormatException {
//        BigDecimal cur = new BigDecimal(data);
//        if(isCeil){
//            return cur.setScale(DoubleScale, BigDecimal.ROUND_CEILING)
//        }else if(isRound){
//            return formatInt((int)Math.round(data));
//        }else {
//            return formatInt(data.intValue());
//        }
        return null;
    }


    private Double formatString(String data) throws FormatException {
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

    private Double formatInt(Integer data) throws FormatException {
        return formatDouble(data.doubleValue());
    }

    private Double formatBigDecimal(BigDecimal data) throws FormatException {
        return formatDouble(data.doubleValue());
    }

    private Double formatLong(Long data) throws FormatException {
        return formatInt(data.intValue());
    }
    private Double formatFloat(Float data) throws FormatException {
        return formatDouble(data.doubleValue());
    }

    private DoubleFormat (){}

    public static DoubleFormat build(){
        return new DoubleFormat();
    }

    public DoubleFormat unString(){
        this.unString = true;
        return this;
    }

    public DoubleFormat unAll(){
        this.unString = true;
        return this;
    }



    public DoubleFormat toNegative(){
        this.toNegative = true;
        return this;
    }

    public DoubleFormat max(Double max){
        this.max = max;
        return this;
    }

    public DoubleFormat min(Double min){
        this.min = min;
        return this;
    }

    public DoubleFormat defaultValue(Double defaultValue){
        this.defaultValue = defaultValue;
        return this;
    }

    public DoubleFormat butNull(){
        this.butNull = true;
        return this;
    }


    public DoubleFormat toPositive(){
        this.toPositive = true;
        return this;
    }

    public DoubleFormat isRound(){
        decimalRoundMode = RoundingMode.HALF_UP;
        return this;
    }

    public DoubleFormat isCeil(){
        decimalRoundMode = RoundingMode.CEILING;
        return this;
    }

    public DoubleFormat isRound(int scale){
        decimalScale = scale;
        decimalRoundMode = RoundingMode.HALF_UP;
        return this;
    }

    public DoubleFormat isCeil(int scale){
        decimalScale = scale;
        decimalRoundMode = RoundingMode.CEILING;
        return this;
    }

    public DoubleFormat scale(int scale){
        decimalScale = scale;
        return this;
    }

    public DoubleFormat digit(int digit){
        this.digit = digit;
        return this;
    }
}
