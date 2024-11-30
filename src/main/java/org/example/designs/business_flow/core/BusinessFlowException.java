package org.example.designs.business_flow.core;

/**
 * Data异常
 *
 * <p>
 *      TODO
 * </p>
 *
 * @author  HaiYu
 * @version 1.0.0
 * @create  2024-11-19
 */
public class BusinessFlowException extends Exception{
    public BusinessFlowException(String message){
        super(message);
    }

    public BusinessFlowException(String message, Throwable e) {
        super(String.format("%s\n-> %s: %s",
                message
                ,e.getClass().getName()
                ,e.getMessage()));
        this.setStackTrace(e.getStackTrace());
    }
}
