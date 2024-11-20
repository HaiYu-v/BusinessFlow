package org.example.designs.taskCollection.strategy.after.fail;


import org.example.designs.task.AbstractTask;


/**
 * @Program: 0.test
 * @Author: 海里的鱼
 * @Create: 2024-08-26 15:57
 * @Name: 处理失败任务的策略
 * @Desc: TODO
 */
public interface IFailStrategy {
    void processError(AbstractTask task);
}
