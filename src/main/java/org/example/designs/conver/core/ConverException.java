package org.example.designs.conver.core;

/**
 * 转换异常
 *
 * <p>
 *     TODO
 * </p>
 *
 * @author HaiYu
 * @version 1.0.0
 * @date 2024-11-21
 */
public class ConverException extends Exception{
    public ConverException(String message) {
        super(message);
    }

    public ConverException(Exception e) {
        super(e);
    }
}
