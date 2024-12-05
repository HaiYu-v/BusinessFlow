package org.example.designs.formatter.format.number;

import org.example.designs.formatter.FormatException;
import org.example.designs.formatter.format.IFormat;
import org.example.designs.formatter.util.NumberUtil;

import java.math.BigDecimal;
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
public abstract class NumberFormat<F extends NumberFormat,R extends Number> implements IFormat<Object,R> {

    //可以为null
    protected boolean butNull = false;
    //支持字符串
    protected boolean unString = false;
    //这是负数
    protected boolean toNegative = false;
    //这是正数
    protected boolean toPositive = false;
    //最大值
    protected R max = null;
    //最小值
    protected R min = null;
    //默认值
    protected R defaultValue = null;
    //整数位数（默认十位）
    protected int digit = 10;
    //直接取整（默认）
    //还支持四舍五入和向上取整
    protected RoundingMode roundMode = RoundingMode.FLOOR;
    //保留位数
    protected int scale = -1;

    @Override
    public R format(Object data) throws FormatException {
        R ret = defaultValue;

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
        }else {
            throw new FormatException(String.format("不支持类型[%s]",data.getClass().getName()));
        }

        //判断为空
        if(null == ret && !butNull)
            throw new FormatException("数据为null且无默认值");

        //位数判断
        if(NumberUtil.getDigitCount(ret.longValue()) > digit)
            throw new FormatException(String.format("[%s]的整数位超过了[%d]位",ret,digit));

        return ret;
    }
    protected abstract R formatInt(Integer data) throws FormatException;
    protected abstract R formatDouble(Double data) throws FormatException;
    protected abstract R formatString(String data) throws FormatException;
    protected abstract R formatBigDecimal(BigDecimal data) throws FormatException;
    protected abstract R formatLong(Long data) throws FormatException;
    protected abstract R formatFloat(Float data) throws FormatException;


    //可为null
    public F butNull(){
        this.butNull = true;
        return (F) this;
    }

    //不支持字符串
    public F unString(){
        this.unString = true;
        return (F) this;
    }

    //转正数
    public F toPositive(){
        this.toPositive = true;
        return (F) (F) this;
    }

    //转负数
    public F toNegative(){
        this.toNegative = true;
        return (F) this;
    }

    //最大值
    public F max(R max){
        this.max = max;
        return (F) this;
    }

    //最小值
    public F min(R min){
        this.min = min;
        return (F) this;
    }

    //默认值
    public F defaultValue(R defaultValue){
        this.defaultValue = defaultValue;
        return (F) this;
    }

    //四舍五入
    public F isRound(){
        this.roundMode = RoundingMode.HALF_UP;
        return (F) this;
    }

    //向上取整
    public F isCeil(){
        this.roundMode = RoundingMode.CEILING;
        return (F) this;
    }

    //保留位数+四舍五入
    public F isRound(int scale){
        this.scale = scale;
        this.roundMode = RoundingMode.HALF_UP;
        return (F) this;
    }

    //保留位数+向上取整
    public F isCeil(int scale){
        this.scale = scale;
        this.roundMode = RoundingMode.CEILING;
        return (F) this;
    }

    //保留几位整数
    public F scale(int scale){
        this.scale = scale;
        return (F) this;
    }

    //整数的最大位数
    public F digit(int digit){
        this.digit = digit;
        return (F) this;
    }


}
