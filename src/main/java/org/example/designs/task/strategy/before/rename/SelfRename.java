package org.example.designs.task.strategy.before.rename;

import org.example.designs.task.AbstractTask;
import org.example.designs.task.strategy.before.IBeforeExecute;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @Program: 0.test
 * @Author: 海里的鱼
 * @Create: 2024-08-26 20:30
 * @Name: TODO
 * @Desc: TODO
 */
public class SelfRename implements IBeforeExecute {
    private static AtomicLong start = new AtomicLong(1L);
    private String prefix = "UnknownTask:";

    @Override
    public void beforeExecute(AbstractTask task) {
        if(task.getDesc() == null || task.getDesc().isEmpty()){
            task.setDesc(prefix + AutoIncrementRename.getStart());
        }
    }
}
