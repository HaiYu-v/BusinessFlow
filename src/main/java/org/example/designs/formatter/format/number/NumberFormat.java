package org.example.designs.formatter.format.number;

import org.example.designs.formatter.FormatException;
import org.example.designs.formatter.format.AbsFormat;
import org.example.designs.formatter.util.NumberUtil;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 数字的抽象格式器
 *
 * <p>
 *     - 约束条件
 *      - 转成百分比，不支持百分比，保留位数
 *      - 正负数
 *      - 整数位数（默认十位）
 *      - 保留位数，直接取整
 *
 *     - 泛型
 *      - F 子类格式器类型，父类的泛型是子类，用于实现父子类方法之间的链式调用
 *      - R 返回类型，格式化后，返回的数据类型
 * </p>
 *
 * @author HaiYu
 * @version 1.0.0
 * @date 2024-12-04
 */
public abstract class NumberFormat<F extends NumberFormat,R extends Number>
        extends AbsFormat<F,R> {
    //百分比保留位数
    protected int percentScale = 2;
    //转成百分比
    protected boolean toPercent = false;
    //不支持百分比
    protected boolean unPercent = false;
    //这是负数
    protected boolean toNegative = false;
    //这是正数
    protected boolean toPositive = false;
    //整数最大位数（默认十位）
    protected int digit = 10;
    //直接取整（默认）
    //还支持四舍五入和向上取整
    protected RoundingMode roundMode = RoundingMode.FLOOR;
    //保留位数
    protected Integer scale = null;


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * 格式化方法
     *
     * @param data 传入数据
     * @return {@link R } 返回格式化后的值
     * @throws FormatException 格式化异常
     */
    @Override
    public R format(Object data) throws FormatException {
        R ret = defaultValue;

        if(data instanceof Integer){
            ret = formatInt((Integer)data);
        }else if(data instanceof Long){
            ret = formatLong((Long) data);
        }else if(data instanceof Float){
            ret = formatFloat((Float) data);
        }else if(data instanceof Double){
            ret = formatDouble((Double) data);
        }else if(data instanceof BigDecimal){
            ret = formatBigDecimal((BigDecimal) data);
        }else if(data instanceof String){
            String str = (String) data;
            if(!str.isEmpty()) ret = formatString(str);
        }else {
            if(null == data && butNull){
                return null;
            }
            throw new FormatException(String.format("不支持类型[%s]",(null == data?"null":data.getClass().getName())));
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

    //不支持百分比
    public F unPercent(){
        this.unPercent = true;
        return (F) this;
    }
    //转成百分比
    public F toPercent(){
        this.toPercent = true;
        return (F) this;
    }
    //转成百分比
    public F toPercentScale(int scale){
        this.percentScale = scale;
        return (F) this;
    }

    @Override
    public String toStr(Object data) throws FormatException {
        R ret = format(data);
        if(toPercent){
            if(percentScale <0 || percentScale>12)
                throw new FormatException(String.format("百分比保留位数[%d]超过范围",percentScale));
            Double percent = ret.doubleValue() * 100;
            return String.format("%."+percentScale+"f%%",percent);
        }
        return ret.toString();
    }

}
