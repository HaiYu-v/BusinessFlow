package org.example.designs.task;

/**
 * 任务状态枚举
 *
 * <p>
 *     TODO
 * </p>
 *
 * @author HaiYu
 * @version 1.0.0
 * @date 2024-11-22
 */
public enum TaskStatusEnum {
    //4.定义枚举
    UN_EXECUTED                (0,"未执行"),
    EXECUTING                  (1,"正在执行"),
    SUCCESS                    (2,"执行成功"),
    EXCEPTION                  (3,"执行异常"),
    FAILED                     (4,"执行失败");

    //1.定义属性
    private int code; //状态码
    private String message; //描述信息

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    //2. 定义构造
    TaskStatusEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
