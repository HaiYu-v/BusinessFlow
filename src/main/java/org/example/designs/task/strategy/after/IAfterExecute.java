package org.example.designs.task.strategy.after;

import lombok.extern.slf4j.Slf4j;
import org.example.designs.task.AbstractTask;


public interface IAfterExecute {
    boolean afterExecute(AbstractTask task,Exception e);
}
