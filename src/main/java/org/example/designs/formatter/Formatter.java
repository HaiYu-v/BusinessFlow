package org.example.designs.formatter;

import org.example.designs.formatter.format.IFormat;
import org.example.designs.formatter.format.dateTime.DateFormat;
import org.example.designs.formatter.format.dateTime.DateTimeFormat;
import org.example.designs.formatter.format.dateTime.TimeFormat;
import org.example.designs.formatter.format.number.BigDecimalFormat;
import org.example.designs.formatter.format.number.DoubleFormat;
import org.example.designs.formatter.format.number.IntegerFormat;
import org.example.designs.formatter.format.number.LongFormat;

import java.util.HashMap;
import java.util.Map;

/**
 * 格式器
 *
 * <p>
 *     TODO
 * </p>
 *
 * @author HaiYu
 * @version 1.0.0
 * @date 2024-12-09
 */
public class Formatter {
    private Map<String, IFormat> formatMap = new HashMap<>();


    public <T,R> R format(T data,String key) throws FormatException {
        IFormat<T,R> iFormat = formatMap.get(key);
        if(null == iFormat) throw new FormatException(String.format("未找到对应的格式器[%s]",key));
        return iFormat.format(data);
    }

    public <T> String toStr(T data,String key) throws FormatException {
        IFormat<T,?> iFormat = formatMap.get(key);
        if(null == iFormat) throw new FormatException(String.format("未找到对应的格式器[%s]",key));
        return iFormat.toStr(data);
    }

    public IFormat put(String key, IFormat format){
        formatMap.put(key,format);
        return format;
    }

    public IFormat remove(String key){
        return formatMap.remove(key);
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * 获取格式器
     * 
     * 个人建议少用这个方法，因为获取到格式器后，
     * 就可以很容易地去修改格式器的约束条件，从而破坏格式统一
     *
     * @param key
     * @return {@link IFormat }
     */
    public IFormat get(String key){
        return formatMap.get(key);
    }
    
    public IntegerFormat putIntegerFormat(String key){
        IntegerFormat format = IntegerFormat.build();
        formatMap.put(key,format);
        return format;
    }
    public LongFormat putLongFormat(String key){
        LongFormat format = LongFormat.build();
        formatMap.put(key,format);
        return format;
    }
    public DoubleFormat putDoubleFormat(String key){
        DoubleFormat format = DoubleFormat.build();
        formatMap.put(key,format);
        return format;
    }
    public BigDecimalFormat putBigDecimalFormat(String key){
        BigDecimalFormat format = BigDecimalFormat.build();
        formatMap.put(key,format);
        return format;
    }
    public TimeFormat putTimeFormat(String key){
        TimeFormat format = TimeFormat.build();
        formatMap.put(key,format);
        return format;
    }
    public DateFormat putDateFormat(String key){
        DateFormat format = DateFormat.build();
        formatMap.put(key,format);
        return format;
    }
    public DateTimeFormat putDateTimeFormat(String key){
        DateTimeFormat format = DateTimeFormat.build();
        formatMap.put(key,format);
        return format;
    }

    public Map<String, IFormat> getFormatMap() {
        return formatMap;
    }

    public void setFormatMap(Map<String, IFormat> formatMap) {
        this.formatMap = formatMap;
    }
}
