package org.example.designs.formatter;

import java.math.BigDecimal;
import java.time.Instant;
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
    INTEGER("整数",Integer.class),
    LONG("长整型",Long.class),
    DOUBLE("浮点数",Double.class),
    BIG_DECIMAL("高精度浮点型", BigDecimal.class),

    DATE("日期", LocalDate.class),
    DATE_STRING("日期字符串", String.class),
    TIMESTAMP("时间戳", Instant.class);

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
