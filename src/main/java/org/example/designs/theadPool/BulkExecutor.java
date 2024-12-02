package org.example.designs.theadPool;



import org.example.designs.task.AbstractTask;
import org.example.designs.task.TaskStatusEnum;
import org.example.designs.task.postProcessor.after.IAfterPostProcessor;
import org.example.designs.task.postProcessor.after.fail.RetryFail;
import org.example.designs.task.postProcessor.before.IBeforePostProcessor;
import org.example.designs.task.postProcessor.before.rename.AutoIncrementRename;
import org.example.designs.task.TaskCollection;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;

/**
 * @Program: 8.mindmap
 * @Author: 海里的鱼
 * @Create: 2024-08-26 10:26
 * @Name: TODO
 * @Desc: TODO
 */
public class BulkExecutor<T extends AbstractTask> {
    //开始时间
    private LocalDateTime startTime;
    //结束时间
    private LocalDateTime endTime;
    //线程池
    private static volatile ExecutorService threadPool;
    //任务集合
    private TaskCollection<T> taskCollection;
    //成功任务集合
    private List<String> successTasks = new ArrayList<>();
    //失败任务集合
    private List<String> failTasks = new ArrayList<>();
    //重试任务集合
    private List<String> retryTasks = new ArrayList<>();
    //任务执行前策略
    private IBeforePostProcessor beforeStrategy = new AutoIncrementRename();
    //任务执行失败后的策略
    private IAfterPostProcessor errorStrategy = new RetryFail();

    /** ---------------------------------------------------------------------------------------------------------------------
     * @Method  : 构造方法
     * @Describe: TODO
     **/
     public BulkExecutor(TaskCollection<T> taskCollection) {
        this.taskCollection = taskCollection;
        //单例的懒汉模式
         if (threadPool == null) {
             synchronized (BulkExecutor.class) {
                 if (threadPool == null) {
                     threadPool = BulkThreadPool.executorService();
                 }
             }
         }
    }

    public BulkExecutor() {
        //单例的懒汉模式
        if (threadPool == null) {
            synchronized (BulkExecutor.class) {
                if (threadPool == null) {
                    threadPool = BulkThreadPool.executorService();
                }
            }
        }
    }

    /** ---------------------------------------------------------------------------------------------------------------------
     * @Method  : 添加任务
     * @Describe: TODO
     **/
    public String add(T task) {
        //执行任务重命名策略
        return taskCollection.add(task);

    }

    public int add(Collection<T> tasks) {
        return taskCollection.add(tasks);
    }

    /** ---------------------------------------------------------------------------------------------------------------------
     * @Method  : 执行任务集合
     * @Describe: TODO
     **/
     public boolean executeTasks(){
         //判断任务集是否为空
         if(null == taskCollection  || taskCollection.size() == 0){
             new BulkExecuteException("任务为空").printStackTrace();
             return false;
         }
         //执行前清空信息
         cleanInfo();

         CountDownLatch cdl = new CountDownLatch(taskCollection.size());
         this.startTime = LocalDateTime.now();

         for(AbstractTask task : taskCollection.getTasks()){
             threadPool.execute(()->{
                 try {
                     execute(task);
                 } catch (BulkExecuteException e) {
                     throw new RuntimeException(e);
                 }
                 cdl.countDown();
             });
         }

         try {
             cdl.await();
         } catch (InterruptedException e) {
             new BulkExecuteException(e).printStackTrace();
             return false;
         }

         this.endTime = LocalDateTime.now();
         return failTasks.size() == 0;
    }

    /** ---------------------------------------------------------------------------------------------------------------------
     * @Method  : 执行单个任务
     * @Describe: TODO
     **/
     public boolean executeTask(AbstractTask task){
         threadPool.execute(()->{
             try {
                 execute(task);
             } catch (BulkExecuteException e) {
                 throw new RuntimeException(e);
             }
         });
         return task.getState() == TaskStatusEnum.SUCCESS;
    }

    /* ---------------------------------------------------------------------------------------------------------------------
     * @Method  : 执行任务
     * @Describe: TODO
     **/
    public boolean execute(AbstractTask task) throws BulkExecuteException {
        //判断任务是否为空
        if(null == task){
            new BulkExecuteException("任务为空").printStackTrace();
            return false;
        }
        //设置任务的后置策略
        task.setAfterExecute(errorStrategy);
        //执行任务
        try {
            task.execute();
        } catch (Exception e) {
            throw new BulkExecuteException(e);
        }
        if(task.getState() == TaskStatusEnum.FAILED) failTasks.add(task.getDesc());
        if(task.getState() == TaskStatusEnum.SUCCESS) successTasks.add(task.getDesc());
        //任务结束
        this.endTime = LocalDateTime.now();
        return task.getState() == TaskStatusEnum.SUCCESS;
    }



    /* ---------------------------------------------------------------------------------------------------------------------
     * @Method  : 清空信息
     * @Describe: TODO
     **/
     private void cleanInfo(){
         this.startTime = null;
         this.endTime = null;
         this.successTasks = new ArrayList<>();
         this.failTasks = new ArrayList<>();
         this.retryTasks = new ArrayList<>();
    }




    /** ---------------------------------------------------------------------------------------------------------------------
     * @Method  : 获取所有执行结果
     * @Describe: TODO
     **/
     public BulkInfo getExecuteInfo(){

          BulkInfo bulkInfo = new BulkInfo();
          bulkInfo.setStartTime(this.startTime);
          bulkInfo.setEndTime(this.endTime);
          bulkInfo.setSuccessTasks(successTasks);
          bulkInfo.setFailTasks(failTasks);
          bulkInfo.setExecuteCount(getExecuteCount());
          return bulkInfo;
    }

    public int getExecuteCount() {
         int count = 0;
         for(AbstractTask task: taskCollection.getTasks()){
             count += task.getCountExecute().get();
         }
        return count;
    }

    /** ---------------------------------------------------------------------------------------------------------------------
     * @Method  : 获取任务总数
     * @Describe: TODO
     **/
     public Long NumCount() {
        return 1L;
    }

    /** ---------------------------------------------------------------------------------------------------------------------
     * @Method  : 获取所有任务的执行时间
     * @Describe: TODO
     **/
     public Long getRunningTime(){
        return ChronoUnit.MILLIS.between(startTime, endTime);
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public static ExecutorService getThreadPool() {
        return threadPool;
    }

    public static void setThreadPool(ExecutorService threadPool) {
        BulkExecutor.threadPool = threadPool;
    }

    public TaskCollection<T> getTaskCollection() {
        return taskCollection;
    }

    public void setTaskCollection(TaskCollection<T> taskCollection) {
        this.taskCollection = taskCollection;
    }

    public List<String> getSuccessTasks() {
        return successTasks;
    }

    public void setSuccessTasks(List<String> successTasks) {
        this.successTasks = successTasks;
    }

    public List<String> getFailTasks() {
        return failTasks;
    }

    public void setFailTasks(List<String> failTasks) {
        this.failTasks = failTasks;
    }

    public List<String> getRetryTasks() {
        return retryTasks;
    }

    public void setRetryTasks(List<String> retryTasks) {
        this.retryTasks = retryTasks;
    }

    public IBeforePostProcessor getBeforeStrategy() {
        return beforeStrategy;
    }

    public void setBeforeStrategy(IBeforePostProcessor beforeStrategy) {
        this.beforeStrategy = beforeStrategy;
    }

    public IAfterPostProcessor getErrorStrategy() {
        return errorStrategy;
    }

    public void setErrorStrategy(IAfterPostProcessor errorStrategy) {
        this.errorStrategy = errorStrategy;
    }
}
