package org.example.designs.taskCollection.strategy.name;

import org.example.designs.task.AbstractTask;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Program: 0.test
 * @Author: 海里的鱼
 * @Create: 2024-08-26 15:57
 * @Name: 自增的命名
 * @Desc: TODO
 */
public class AutoIncrementRename implements IRename {
    private AtomicInteger start = new AtomicInteger(1);
    private String prefix = "AbstractTask:";

    public AutoIncrementRename(Integer start, String prefix) {
        this.start = new AtomicInteger(start);
        this.prefix = prefix;
    }
    public AutoIncrementRename(String prefix) {
        this.prefix = prefix;
    }
    public AutoIncrementRename(Integer start) {
        this.start = new AtomicInteger(start);
    }
    public AutoIncrementRename() {

    }

    @Override
    public void rename(AbstractTask task) {
        task.setName(prefix + start.getAndIncrement());
    }
}
