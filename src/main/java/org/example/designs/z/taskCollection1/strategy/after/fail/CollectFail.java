package org.example.designs.taskCollection.strategy.after.fail;

import org.example.designs.task.AbstractTask;

/**
 * @Program: 0.test
 * @Author: 海里的鱼
 * @Create: 2024-08-26 20:20
 * @Name: TODO
 * @Desc: TODO
 */
public class CollectFail implements IFailStrategy {


    @Override
    public void processError(AbstractTask task) {
        handler.addErrorTask(task.getName());
    }


}
