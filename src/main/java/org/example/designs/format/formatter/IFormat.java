package org.example.designs.format.formatter;

/**
 * 格式器接口
 *
 * <p>
 *     TODO
 * </p>
 *
 * @author HaiYu
 * @version 1.0.0
 * @create 2024-12-02
 */
public interface IFormat<T,R> {
    R format(T data);
}
