package org.example.designs.format;

import java.time.LocalDate;
import java.util.Date;

/**
 * 数据类型
 *
 * <p>
 *     TODO
 * </p>
 *
 * @author HaiYu
 * @version 1.0.0
 * @create 2024-12-02
 */
public enum DataType {
    STRING("字符串",String.class),
    NUMBER("数字",Number.class),
    DATE("日期", LocalDate.class);

    private String desc;
    private Class<?> type;

    DataType(String desc, Class<?> type) {
        this.desc = desc;
        this.type = type;
    }


    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Class<?> getType() {
        return type;
    }

    public void setType(Class<?> type) {
        this.type = type;
    }
}
