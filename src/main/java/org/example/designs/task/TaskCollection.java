package org.example.designs.task;



import org.example.designs.task.strategy.before.IBeforeExecute;
import org.example.designs.task.strategy.before.rename.SelfRename;
import org.example.designs.theadPool.BulkExecuteException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * 任务集合
 *
 * <p>
 *     TODO
 * </p>
 *
 * @author HaiYu
 * @version 1.0.0
 * @date 2024-11-22
 */
public class TaskCollection<T extends AbstractTask> {

    private HashMap<String, T> tasks;
    //任务的命名策略
    private IBeforeExecute renameStrategy = new SelfRename();

    public TaskCollection() {
        tasks = new HashMap<>();
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * 添加任务
     *
     * @param task
     * @return {@link String }
     */
    public String add(T task) {
        //执行任务重命名策略
//        renameStrategy.AutoIncrementRename(task);

        if (tasks.containsKey(task.getName())) {
            new BulkExecuteException("任务重名").printStackTrace();
            return null;
        }
        tasks.put(task.getName(), task);
        return task.getName();
    }

    public int add(Collection<T> tasks) {
         for(T task : tasks) {
             add(task);
         }
         return tasks.size();
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * 获取任务
     *
     * @param name
     * @return {@link T }
     */
    public T get(String name) {
        return tasks.get(name);
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * 任务大小
     *
     * @return int
     */
    public int size(){
        return tasks.size();
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * 删除
     *
     * @param name
     * @return {@link String }
     */
    public String remove(String name) {
        AbstractTask task = tasks.remove(name);
        return task == null ?null :task.getName();
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * 任务集合
     *
     * @return {@link List }<{@link T }>
     */
    public List<T> getTasks() {
        return new ArrayList<T>(tasks.values());
    }


    public IBeforeExecute getRenameStrategy() {
        return renameStrategy;
    }

    public void setRenameStrategy(IBeforeExecute renameStrategy) {
        this.renameStrategy = renameStrategy;
    }

    public void setTasks(HashMap<String, T> tasks) {
        this.tasks = tasks;
    }
}
