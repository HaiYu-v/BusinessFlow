package org.example.designs.taskCollection.strategy.name;

import org.example.designs.task.AbstractTask;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @Program: 0.test
 * @Author: 海里的鱼
 * @Create: 2024-08-26 20:30
 * @Name: TODO
 * @Desc: TODO
 */
public class SelfRename implements IRename {
    private AtomicLong start = new AtomicLong(1L);
    private String prefix = "UnknownTask:";

    @Override
    public void rename(AbstractTask task) {
        if(task.getName() == null || task.getName().isEmpty()){
            task.setName(prefix + start.getAndIncrement());
        }
    }
}
