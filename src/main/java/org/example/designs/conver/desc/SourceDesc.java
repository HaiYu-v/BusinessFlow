package org.example.designs.conver.desc;

import cn.hutool.core.util.StrUtil;
import org.example.designs.conver.core.IDataSource;

import java.lang.reflect.Field;

/**
 * 来源描述
 *
 * <p>
 *     记录一个bean的code和他的一个field
 *     如果field为空，则表示此code指向一个数值，而非bean
 *     如果field不为空，则取此code指向bean的field值
 * </p>
 *
 * @author HaiYu
 * @version 1.0.0
 * @date 2024-11-21
 */
public class SourceDesc {
    private String code;
    private String field;

    public Object getValue(IDataSource dataSource) throws Exception{
        //file为空，则直接返回code值
        if(StrUtil.isBlank(field)){
            return dataSource.get(code);
        }
        //file不为空，则从data里取值
        Object data = dataSource.get(code);
        Field declaredField = data.getClass().getDeclaredField(field);
        declaredField.setAccessible(true);
        return declaredField.get(data);
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
        StringBuilder builder = new StringBuilder();
        if(StrUtil.isNotBlank(this.code)){
            builder.append(this.code);
            if (StrUtil.isNotBlank(this.field)){
                builder.append(".").append(this.field);
            }
        }
        return builder.toString();
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
