package org.example.designs.task.postProcessor.after;

import org.example.designs.task.AbstractTask;


public interface IAfterPostProcessor {
    boolean afterExecute(AbstractTask task,Exception e);
}
