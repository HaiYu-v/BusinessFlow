package org.example.designs.conver.desc;

import cn.hutool.core.util.StrUtil;
import org.example.designs.conver.core.IDataSource;

import java.lang.reflect.Field;

/**
 * 来源描述
 *
 * <p>
 *     记录一个bean的code和一个field
 *     一个bean的属性 -> code != null && field != null
 *     一个bean -> code != null && field == null
 *     一个变量 -> code == null && field != null
 * </p>
 *
 * @author HaiYu
 * @version 1.0.0
 * @date 2024-11-21
 */
public class SourceDesc {
    private String code;
    private String field;

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * 获取值
     *
     * @param dataSource 数据源
     * @return {@link Object }
     * @throws Exception
     */
    public Object getValue(IDataSource dataSource){
        try {
            //file为空，则直接返回code对应的bean
            if(StrUtil.isBlank(field)){
                return dataSource.get(code);
            }
            //filed不为空，则返回对应bean的对应filed
            Object data = dataSource.get(code);
            Field declaredField = data.getClass().getDeclaredField(field);
            declaredField.setAccessible(true);
            return declaredField.get(data);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public SourceDesc(String code, String field) {
        this.field = field;
        this.code = code;
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * 获得Key，用于外界的映射
     *
     * @return {@link String }
     */
    public String getKey(){
        if(code!=null){
            return code;
        }
        return field;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }
}
