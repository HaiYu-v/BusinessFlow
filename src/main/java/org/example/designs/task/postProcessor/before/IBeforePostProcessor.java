package org.example.designs.task.postProcessor.before;

import org.example.designs.task.AbstractTask;

public interface IBeforePostProcessor {
    void beforeExecute(AbstractTask task);
}
