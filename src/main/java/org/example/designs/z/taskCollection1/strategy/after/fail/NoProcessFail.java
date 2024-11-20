package org.example.designs.taskCollection.strategy.after.fail;

import org.example.designs.task.AbstractTask;

/**
 * @Program: 0.test
 * @Author: 海里的鱼
 * @Create: 2024-08-26 15:49
 * @Name: 不处理失败任务策略
 * @Desc: TODO
 */
public class NoProcessFail implements IFailStrategy {
    @Override
    public void processError(AbstractTask task) {
    }
}
