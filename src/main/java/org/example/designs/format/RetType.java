package org.example.designs.format;

import java.time.LocalDate;

/**
 * 返回值类型
 *
 * <p>
 *     TODO
 * </p>
 *
 * @author HaiYu
 * @version 1.0.0
 * @create 2024-12-02
 */
public enum RetType {
    STRING("字符串",String.class),
    INTEGER("整数",Integer.class),
    LONG("长整型",Long.class),
    FLOAT("浮点型",Float.class),
    DOUBLE("双精度浮点型",Double.class),
    BIG_DECIMAL("高精度浮点型",java.math.BigDecimal.class),
    DATE("日期", LocalDate.class),
    DATE_STRING("日期字符串", String.class),
    TIMESTAMP("时间戳",java.sql.Timestamp.class);

    private String desc;
    private Class<?> type;

    RetType(String desc, Class<?> type) {
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
