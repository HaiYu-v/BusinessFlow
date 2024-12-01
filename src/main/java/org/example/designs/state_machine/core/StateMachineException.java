package org.example.designs.state_machine.core;

/**
 * 状态机异常
 *
 * <p>
 *     TODO
 * </p>
 *
 * @author HaiYu
 * @version 1.0.0
 * @date 2024-12-01
 */
public class StateMachineException extends Exception{
    public StateMachineException(String message){
        super(message);
    }

    public StateMachineException(String message, Throwable e) {
        super(String.format("%s\n-> %s: %s",
                message
                ,e.getClass().getName()
                ,e.getMessage()));
        this.setStackTrace(e.getStackTrace());
    }
}
