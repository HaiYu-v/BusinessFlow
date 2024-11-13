package org.example.designs.task.strategy.after.fail;

import org.example.designs.task.AbstractTask;
import org.example.designs.task.strategy.after.IAfterExecute;

/**
 * @Program: 11.designs
 * @Author: 海里的鱼
 * @Create: 2024-10-11 13:40
 * @Name: TODO
 * @Desc: 不抛出异常
 */
public class UnThrow implements IAfterExecute {
    @Override
    public boolean afterExecute(AbstractTask task, Exception e) {
        return false;
    }
}
