package org.example.designs.task;



import org.example.designs.task.strategy.before.IBeforeExecute;
import org.example.designs.task.strategy.before.rename.SelfRename;
import org.example.designs.theadPool.BulkExecuteException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * @Program: 0.test
 * @Author: 海里的鱼
 * @Create: 2024-08-27 20:51
 * @Name: 任务集合类
 * @Desc: TODO
 */
public class TaskCollection<T extends AbstractTask> {

    private HashMap<String, T> tasks;
    //任务的命名策略
    private IBeforeExecute renameStrategy = new SelfRename();

    public TaskCollection() {
        tasks = new HashMap<>();
    }



    /** ---------------------------------------------------------------------------------------------------------------------
     * @Method  : 添加任务
     * @Describe: TODO
     **/
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

    /** ---------------------------------------------------------------------------------------------------------------------
     * @Method  : 获取任务
     * @Describe: TODO
     **/
    public T get(String name) {
        return tasks.get(name);
    }

    /** ---------------------------------------------------------------------------------------------------------------------
     * @Method  : 任务大小
     * @Describe: TODO
     **/
    public int size(){
        return tasks.size();
    }

    /** ---------------------------------------------------------------------------------------------------------------------
     * @Method  : 删除任务
     * @Describe: TODO
     **/
    public String remove(String name) {
        AbstractTask task = tasks.remove(name);
        return task == null ?null :task.getName();
    }

    /**
     * ---------------------------------------------------------------------------------------------------------------------
     *
     * @Method : 获取任务集合
     * @Describe: TODO
     **/
    public List<T> getTasks() {
        return new ArrayList<T>(tasks.values());
    }


//    public IRename getRenameStrategy() {
//        return renameStrategy;
//    }
//
//    public void setRenameStrategy(IRename renameStrategy) {
//        this.renameStrategy = renameStrategy;
//    }
}
