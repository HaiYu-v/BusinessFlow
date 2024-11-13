package org.example.designs.task.strategy.after.fail;

import org.example.designs.task.AbstractTask;
import org.example.designs.task.strategy.after.IAfterExecute;

/**
 * @Program: 11.designs
 * @Author: 海里的鱼
 * @Create: 2024-11-12 18:33
 * @Name: TODO
 * @Desc: TODO
 */
public class Throw implements IAfterExecute {
    @Override
    public boolean afterExecute(AbstractTask task, Exception e) {
        return true;
    }
}
