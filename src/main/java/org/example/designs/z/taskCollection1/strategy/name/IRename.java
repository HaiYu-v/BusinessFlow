package org.example.designs.taskCollection.strategy.name;

import org.example.designs.task.AbstractTask;


/**
 * @Program: 0.test
 * @Author: 海里的鱼
 * @Create: 2024-08-26 15:57
 * @Name: 任务重命名策略的接口
 * @Desc: TODO
 */
public interface IRename {
    void rename(AbstractTask task);
}
