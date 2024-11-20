package org.example.designs.chain1;

/**
 * 产品状态信息的枚举
 */
public enum DataCode {
    //定义枚举
    NORMAL                    ((byte) 0,"正常产品"),
    ABNORMAL                  ((byte) 1,"异常产品"),
    NO_PROCESSING             ((byte) 2,"不需再处理的产品");
    //定义属性
    private byte code; //状态码
    private String message; //描述信息

    //定义构造
    DataCode(byte code, String message) {
        this.code = code;
        this.message = message;
    }

    //3. 生成get方法
    public int getCode() {
        return code;
    }
    public String getMessage() {
        return message;
    }
}
