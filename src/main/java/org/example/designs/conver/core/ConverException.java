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

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * 继承源异常的栈帧和消息
     *
     * 如果把e传给super，那么栈帧就会续上
     *  不把e传给super，那么栈帧就会一直是最初的栈帧
     *
     * @param message
     * @param e
     */
    public ConverException(String message, Exception e) {
        super(new StringBuilder()
                .append(message)
                .append("\n-> ")
                .append(e.getClass().getName())
                .append(": ")
                .append(e.getMessage())
                .toString());
        this.setStackTrace(e.getStackTrace());
    }
}
