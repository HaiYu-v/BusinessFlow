package org.example.designs.conver.desc;

import cn.hutool.core.util.StrUtil;
import org.example.designs.conver.core.IDataSource;

import java.lang.reflect.Field;

/**
 * 来源描述
 *
 * <p>
 *     记录一个code和他的一个field
 *     如果field为空，则表示此code指向一个数值，而非Bean
 *     如果field不为空，则取bean的此field值
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
        //file不为空，则从bean里取值
        Object bean = dataSource.get(code);
        Field declaredField = bean.getClass().getDeclaredField(field);
        declaredField.setAccessible(true);
        return declaredField.get(bean);
    }

    public SourceDesc(String code, String field) {
        this.field = field;
        this.code = code;
    }
}
