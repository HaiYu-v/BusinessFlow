package org.example.designs.taskCollection.strategy.name;

import org.example.designs.task.AbstractTask;

import java.util.UUID;

/**
 * @Program: 0.test
 * @Author: 海里的鱼
 * @Create: 2024-08-26 16:08
 * @Name: 使用UUID来重命名任务
 * @Desc: TODO
 */
public class UUIDRename implements IRename {
    private String prefix = "AbstractTask:";

    public UUIDRename(String prefix) {
        this.prefix = prefix;
    }
    public UUIDRename() {}

    @Override
    public void rename(AbstractTask task) {
        task.setName(prefix + UUID.randomUUID());
    }
}
