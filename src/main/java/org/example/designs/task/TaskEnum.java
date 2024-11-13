package org.example.designs.task;
/**
 * @Program: 0.test
 * @Author: 海里的鱼
 * @Create: 2024-08-26 15:57
 * @Name: 任务状态的枚举
 * @Desc: TODO
 */
public enum TaskEnum {
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
    TaskEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
