package org.example.designs.theadPool;


import org.example.designs.task.AbstractTask;
import org.example.designs.task.postProcessor.after.IAfterPostProcessor;
import org.example.designs.task.TaskCollection;

/**
 * @Program: 8.mindmap
 * @Author: 海里的鱼
 * @Create: 2024-08-26 09:01
 * @Name: 任务工具类
 * @Desc: TODO
 */
public class BulkExecuteUtil {

    public static <T extends AbstractTask> BulkExecutor<T> getExecutor(TaskCollection<T> taskCollection){
        return new BulkExecutor<T>(taskCollection);
    }

    public static BulkExecutor getExecutor(){
        return new BulkExecutor();
    }

    public static <T extends AbstractTask> BulkExecutor<T> getExecutor(TaskCollection<T> taskCollection, IAfterPostProcessor strategy){
        BulkExecutor<T> executor = new BulkExecutor<T>(taskCollection);
        executor.setErrorStrategy(strategy);
        return executor;
    }


    public static BulkExecutor getExecutor(IAfterPostProcessor strategy){
        BulkExecutor executor = new BulkExecutor();
        executor.setErrorStrategy(strategy);
        return executor;
    }

    public static <T extends AbstractTask> TaskCollection<T> getTaskCollection(Class<T> classType){
        return new TaskCollection<T>();
    }

    

}
