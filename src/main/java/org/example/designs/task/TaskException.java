package org.example.designs.task;

import org.example.designs.utils.MyStrUtil;

/**
 * 任务异常
 *
 * <p>
 *     TODO
 * </p>
 *
 * @author HaiYu
 * @version 1.0.0
 * @date 2024-11-22
 */
public class TaskException extends Exception{
    public TaskException(String message) {
        super(message);
    }


    public TaskException(String message, Exception e) {
        super(String.format("%s\n-> %s: %s",
                message
                ,e.getClass().getName()
                ,e.getMessage()));
        this.setStackTrace(e.getStackTrace());
    }
}
