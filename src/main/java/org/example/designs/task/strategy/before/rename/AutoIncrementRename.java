package org.example.designs.task.strategy.before.rename;

import org.example.designs.task.AbstractTask;
import org.example.designs.task.strategy.before.IBeforeExecute;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Program: 11.designs
 * @Author: 海里的鱼
 * @Create: 2024-11-05 10:33
 * @Name: TODO
 * @Desc: TODO
 */
public class AutoIncrementRename implements IBeforeExecute {
    private static AtomicInteger start = new AtomicInteger(1);
    private String prefix = "Task:";

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
    public void beforeExecute(AbstractTask task) {
        task.setName(prefix + start.getAndIncrement());
    }

    public static int getStart() {
        return start.getAndIncrement();
    }
}
