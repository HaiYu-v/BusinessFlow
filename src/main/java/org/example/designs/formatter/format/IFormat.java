package org.example.designs.formatter.format;

import org.example.designs.formatter.FormatException;

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
    R format(T data) throws FormatException;
    String toStr(T data) throws FormatException;
}
