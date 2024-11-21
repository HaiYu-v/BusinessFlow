package org.example.designs.conver;

/**
 * 转换器的数据源
 *
 * <p>
 *     转换器从这里获取数据来用于转换
 * </p>
 *
 * @author HaiYu
 * @version 1.0.0
 * @create 2024-11-21
 */
public interface IDataSource {
    Object getValue(String key);
    <T> T getValue(String key,Class<T> valueType);
}
