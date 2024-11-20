package org.example.designs.taskCollection;
/**
 * @Program: 0.test
 * @Author: 海里的鱼
 * @Create: 2024-08-26 15:57
 * @Name: 任务状态的枚举
 * @Desc: TODO
 */
public enum TaskCollectionEnum {
    //4.定义枚举
    UN_EXECUTE                 (0,"未执行"),
    EXECUTING                  (1,"正在执行"),
    PART_SUCCESS               (2,"部分执行成功"),
    ALL_SUCCESS                (3,"全部执行成功"),
    ALL_FAILED                 (4,"全部执行失败");

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
    TaskCollectionEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
