package org.example.designs.chain.desc;

import java.lang.reflect.Field;

/**
 * 转换点描述
 *
 * <p>
 *     TODO
 * </p>
 *
 * @author HaiYu
 * @version 1.0.0
 * @date 2024-11-20
 */
public class ConvertDesc<T> {



    // 目标对象
    private T targetBean;
    // 目标对象的目标字段
    private Field targetField;
    // 源对象
    private Object sourceBean;
    // 源对象的源字段
    private Field sourceField;
}
