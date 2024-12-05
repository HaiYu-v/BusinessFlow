package org.example.designs.formatter.format;

import java.time.LocalDateTime;

/**
 * TODO
 *
 * <p>
 *     TODO
 * </p>
 *
 * @author HaiYu
 * @version 1.0.0
 * @date 2024-12-05
 */
public class AbsFormat<F extends AbsFormat,R> {
    //可以为null
    protected boolean butNull = false;
    //支持字符串
    protected boolean unString = false;
    //最大值
    protected R max = null;
    //最小值
    protected R min = null;
    //默认值
    protected R defaultValue = null;

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
}
