package org.example.designs.theadPool;

/**
 * @Program: 8.mindmap
 * @Author: 海里的鱼
 * @Create: 2024-08-27 14:14
 * @Name: 批量导入的异常
 * @Desc: TODO
 */
public class BulkExecuteException extends Exception{
    public BulkExecuteException(String message) {
        super(message);
    }
    public BulkExecuteException(Exception e){
        super(e);
    }
}
