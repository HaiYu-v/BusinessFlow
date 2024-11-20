package org.example.designs.chain.desc;

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
public class SourceDesc<T> {
    //来源bean
    private T bean;
    //来源字段
    private Field field;
    //来源字段中文
    private String fieldDesc;
    //来源值
    private Object value;
    //转换公式
    private String formula;
}
