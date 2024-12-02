package org.example.designs.format;

import org.example.designs.format.formatter.IFormat;

import java.util.HashMap;
import java.util.Map;

/**
 * 格式器
 *
 * <p>
 *     1.数字格式化
 *     2.日期格式化
 *     3.字符串格式化
 * </p>
 *
 * @author HaiYu
 * @version 1.0.0
 * @date 2024-12-02
 */
public class Formatter {

    Map<String,IFormat> formatMap;

    public Formatter() {
        this.formatMap = new HashMap<>();
    }

    public void register(String desc, Class<?> dataType , Class<?> retType, IFormat format) {
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * 注册格式器，以CLass为Key
     *
     * @param dataType 数据类型
     * @param format   格式器
     * @return
     */
    public <T> IFormat register(Class<T> dataType, IFormat<T,?> format) {
        return formatMap.put(dataType.getName(),format);
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * 格式化，根据Class匹配
     *
     * @param data 数据
     * @return {@link R }
     */
    public <R,T> R format(T data){
        IFormat<T,R> format = formatMap.get(data.getClass().getName());
        return format.format(data);
    }
}
