package org.example.designs.task;

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

    public TaskException(Exception e) {
        super(e);
    }
}
