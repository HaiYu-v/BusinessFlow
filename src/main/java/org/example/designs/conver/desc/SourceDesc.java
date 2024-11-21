package org.example.designs.conver.desc;

import org.example.designs.conver.IDataSource;

import java.lang.reflect.Field;

/**
 * 来源描述描述
 *
 * <p>
 *     TODO
 * </p>
 *
 * @author HaiYu
 * @version 1.0.0
 * @date 2024-11-20
 */
public class SourceDesc {
    //转换方式
    private String type;
    //来源beanCode
    private String code;
    //来源值
    private Object value;
    //来源字段
    private String field;
    //转换公式
    private String formula;

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * 获取目标值
     *
     * 通过源值，计算获得目标值
     *
     * @return 计算值
     */
    public Object getTargetValue(IDataSource dataSource) throws IllegalAccessException, NoSuchFieldException {
        if(null == value){
            value = dataSource.getValue(code);
        }
        Field field = this.value.getClass().getField(this.field);
            return field.get(this.value);

    }

    public SourceDesc(String type, String code, Object value, String field, String formula) {
        this.type = type;
        this.code = code;
        this.value = value;
        this.field = field;
        this.formula = formula;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }
}
