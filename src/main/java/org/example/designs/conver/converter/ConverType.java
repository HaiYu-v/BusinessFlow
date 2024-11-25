package org.example.designs.conver.converter;

/**
 * 转换方式的枚举
 *
 * <p>
 *     0 —— 简单映射
 *     1 —— 常量
 *     2 —— 公式计算
 * </p>
 *
 * @author HaiYu
 * @version 1.0.0
 * @create 2024-11-21
 */
public enum ConverType {

    SIMPLE_MAPPING("0", "简单映射"),
    CONSTANT("1", "常量"),
    FORMULA_CALCULATION("2", "公式计算");

    public final String code;
    public final String type;

    ConverType(String code, String type) {
        this.code = code;
        this.type = type;
    }

    public static ConverType ValueOf(String code) {
        for (ConverType type : ConverType.values()) {
            if (type.code.equals(code)) {
                return type;
            }
        }
        return null;
    }

    public String getCode() {
        return code;
    }

    public String getType() {
        return type;
    }
}
