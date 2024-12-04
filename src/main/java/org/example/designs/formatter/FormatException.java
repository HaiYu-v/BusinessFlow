package org.example.designs.formatter;

/**
 * 格式化异常
 *
 * <p>
 *     TODO
 * </p>
 *
 * @author HaiYu
 * @version 1.0.0
 * @date 2024-12-04
 */
public class FormatException extends Exception{
    public FormatException(String message){
        super(message);
    }

    public FormatException(String message, Throwable e) {
        super(String.format("%s\n-> %s: %s",
                message
                ,e.getClass().getName()
                ,e.getMessage()));
        this.setStackTrace(e.getStackTrace());
    }
}
