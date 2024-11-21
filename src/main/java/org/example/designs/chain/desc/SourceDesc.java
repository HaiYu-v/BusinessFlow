package org.example.designs.chain.desc;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

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
    private Object bean;
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
    public Object getTargetValue() throws IllegalAccessException, NoSuchFieldException {
            Field field = this.bean.getClass().getField(this.field);
            return field.get(this.bean);

    }

    public SourceDesc(String type, String code, Object bean, String field, String formula) {
        this.type = type;
        this.code = code;
        this.bean = bean;
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

    public Object getBean() {
        return bean;
    }

    public void setBean(Object bean) {
        this.bean = bean;
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
