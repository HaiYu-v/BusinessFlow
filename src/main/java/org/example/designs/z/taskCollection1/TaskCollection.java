package org.example.designs.taskCollection;

import org.example.designs.task.AbstractTask;
import org.example.designs.task.TaskException;
import org.example.designs.taskCollection.strategy.after.fail.CollectFail;
import org.example.designs.taskCollection.strategy.after.fail.IFailStrategy;
import org.example.designs.taskCollection.strategy.name.IRename;
import org.example.designs.taskCollection.strategy.name.SelfRename;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: 海里的鱼
 * @Create: 2024-08-27 20:51
 * @Name: 任务集合类
 * @Desc: TODO
 */
public class TaskCollection<T extends AbstractTask> implements Collection<T> {
    //任务集状态
    private TaskCollectionEnum state = TaskCollectionEnum.UN_EXECUTE;
    //任务集合(任务id,任务对象)
    private Map<Integer, T> taskMap = new HashMap<>();;
    //成功任务集合
    private List<T> successTasks = new ArrayList<>();
    //失败任务集合
    private List<T> failTasks = new ArrayList<>();
    //重试任务集合(任务id,重试次数)
    private Map<Integer,Integer> retryTasks = new HashMap<>();
    //任务执行痕迹
    private LinkedList<Integer> executeTrace = new LinkedList<>();
    //开始时间
    protected LocalDateTime startTime;
    //结束时间
    protected LocalDateTime endTime;
    //任务的命名策略
    private IRename renameStrategy = new SelfRename();
    //任务执行失败后的重试策略
    private IFailStrategy errorStrategy = new CollectFail();
    //重试次数
    private AtomicInteger retryCount = new AtomicInteger(0);
    //下一个任务的id
    private AtomicInteger nextTaskID = new AtomicInteger(0);

    /** ---------------------------------------------------------------------------------------------------------------------
     * @Method  : 构造方法
     * @Describe: TODO
     **/
    public TaskCollection() {
    }


    /** ---------------------------------------------------------------------------------------------------------------------
     * @Method  : 开启任务集合的执行
     * @Describe: TODO
     **/
     public boolean start(){
        //判断任务集合是否能够启动
        if(state == TaskCollectionEnum.EXECUTING){
            return false;
        }
        //清空上次执行信息
        cleanInfo();

        startTime = LocalDateTime.now();
        state = TaskCollectionEnum.EXECUTING;
        return state == TaskCollectionEnum.EXECUTING;
    }

    /** ---------------------------------------------------------------------------------------------------------------------
     * @Method  : 结束任务集的执行
     * @Describe: TODO
     **/
     public boolean end(){
        //只有正在执行的任务集才能结束
        if(state != TaskCollectionEnum.EXECUTING){
            return false;
        }
        if(successTasks.isEmpty()){
            state = TaskCollectionEnum.ALL_FAILED;
        }else if(!failTasks.isEmpty()){
            state = TaskCollectionEnum.PART_SUCCESS;
        }else {
            state = TaskCollectionEnum.ALL_SUCCESS;
        }
        endTime = LocalDateTime.now();
        return true;
    }

    /** ---------------------------------------------------------------------------------------------------------------------
     * @Method  : 执行指定id的任务
     * @Describe: TODO
     **/
     public boolean executeById(Integer id) throws Exception {
        T task = this.get(id);
        if(null == task){
            throw new TaskCollectionException("任务不存在");
        }
        return executeTask(task);
    }

    /** ---------------------------------------------------------------------------------------------------------------------
     * @Method  : 无序执行所有任务
     * @Describe: TODO
     **/
    public boolean executeAll() throws Exception {
        if(null == taskMap){
            return true;
        }
        for(T task : taskMap.values()){
            executeTask(task);
        }
        return true;
    }

    /* ---------------------------------------------------------------------------------------------------------------------
     * @Method  : 执行任务
     * @Describe: TODO
     **/
    private boolean executeTask(T task) throws Exception {
        executeTrace.add(task.getId());
        boolean ret = task.execute();
        if(ret){
            successTasks.add(task);
        }else {
            failTasks.add(task);
            retryTasks.put(task.getId(),retryTasks.getOrDefault(task.getId(),0)+1);
//            errorStrategy.processError(BulkExecuteUtil.getHandler(this),task);
        }

        return ret;
    }

    /* ---------------------------------------------------------------------------------------------------------------------
     * @Method  : 清空任务集信息
     * @Describe: TODO
     **/
    public void cleanInfo(){
        successTasks = new ArrayList<>();
        failTasks = new ArrayList<>();
        retryTasks = new HashMap<>();
        executeTrace = new LinkedList<>();
        state = TaskCollectionEnum.UN_EXECUTE;
        startTime = null;
        endTime = null;
        retryCount = new AtomicInteger(0);
    }

    /** ---------------------------------------------------------------------------------------------------------------------
     * @Method  : 获取任务, 根据任务id
     * @Describe: TODO
     **/
    public T get(Integer id) {
        if(null == taskMap){
            return null;
        }
        return taskMap.get(id);
    }

    /** ---------------------------------------------------------------------------------------------------------------------
     * @Method  : 获取任务, 根据任务名
     * @Describe: TODO
     **/
    public T get(String name) {

        if(null == taskMap){
            return null;
        }

        for(T task : taskMap.values()){
            if(task.getName().equals(name)){
                return task;
            }
        }

        return null;
    }

    /** ---------------------------------------------------------------------------------------------------------------------
     * @Method  : 获取所有任务, 根据任务名
     * @Describe: TODO
     **/
    public List<T> getAllByName(String name){

        if(null == taskMap){
            return null;
        }

        List<T> ret = new ArrayList<>();
        for(T task : taskMap.values()){
            if(task.getName().equals(name)){
                ret.add(task);
            }
        }
        return ret;
    }

    /** ---------------------------------------------------------------------------------------------------------------------
     * @Method  : 添加任务执行顺序
     * @Describe: TODO
     **/
     public void addOrder(Integer id){
         executeTrace.add(id);
     }

     /** ---------------------------------------------------------------------------------------------------------------------
      * @Method  : 获取已执行任务执行顺序
      * @Describe: TODO
      **/
     public int getOrderCount(){
         return executeTrace.size();
     }


    @Override
    public int size() {
        return null == taskMap ? 0 : taskMap.size();
    }

    @Override
    public boolean isEmpty() {
        return null == taskMap || taskMap.size() <= 0;
    }

    @Override
    public boolean contains(Object task) {
        return null == taskMap ? false : taskMap.containsValue(task);
    }

    @Override
    public Iterator<T> iterator() {
        return null == taskMap ? null : taskMap.values().iterator();
    }

    @Override
    public Object[] toArray() {
        return null == taskMap ? null : taskMap.values().toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        if(null != taskMap){
            return taskMap.values().toArray(a);
        }
        return a;
    }

    @Override
    public boolean add(T task) {
        //设置任务id
        task.setId(nextTaskID.getAndIncrement());
        //执行任务重命名策略
        if(task.getName() == null){
            renameStrategy.rename(task);
        }
        //检验id重复
        if (taskMap.containsKey(task.getId())) {
            new TaskException("任务id重复").printStackTrace();
            return false;
        }
        taskMap.put(task.getId(), task);
        return true;
    }


    @Override
    public boolean remove(Object task) {
        return null == taskMap ? false : taskMap.remove(((AbstractTask)task).getId()) != null;
    }

    public T remove(Integer id){
        return null == taskMap ? null : taskMap.remove(id);
    }

    /** ---------------------------------------------------------------------------------------------------------------------
     * @Method  : 是否包含这些任务
     * @Describe: 传入任务ID集合
     **/
     @Override
    public boolean containsAll(Collection<?> c) {
        if(null == taskMap){
            return false;
        }

        for(Object o : c){
            Integer id = (Integer) o;
            if(!taskMap.containsKey(id)){
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean addAll(Collection<? extends T> tasks) {
        for(T task : tasks) {
            if(!add(task)){
                return false;
            }
        }
        return true;
    }

    /** ---------------------------------------------------------------------------------------------------------------------
     * @Method  : 删除指定任务
     * @Describe: 传入任务ID集合
     **/
    @Override
    public boolean removeAll(Collection<?> c) {
        if(null == taskMap){
            return true;
        }

        for(Object o : c){
            Integer id = (Integer) o;
            taskMap.remove(id);
        }
        return true;
    }

    /** ---------------------------------------------------------------------------------------------------------------------
     * @Method  : 求交集
     * @Describe: 传入任务ID集合
     **/
     @Override
    public boolean retainAll(Collection<?> c) {
         if(null == taskMap){
             return true;
         }
         //获取key集合
         Set<Integer> ids = taskMap.keySet();

         //删去ids中,c集合存在的id
         for(Object o : c){
             Integer id = (Integer) o;
             ids.remove(id);
         }

         //删除taskMap中剩余的ids
         return removeAll(ids);
    }

    @Override
    public void clear() {
        this.taskMap.clear();
        this.nextTaskID = new AtomicInteger(0);
        cleanInfo();
    }


    /** ---------------------------------------------------------------------------------------------------------------------
     * @Method : 获取任务集合
     * @Describe: TODO
     **/
    public List<T> getTaskMap() {
        return new ArrayList<T>(taskMap.values());
    }

    public TaskCollectionEnum getState() {
        return state;
    }

    public void setState(TaskCollectionEnum state) {
        this.state = state;
    }

    public void setTaskMap(Map<Integer, T> taskMap) {
        this.taskMap = taskMap;
    }

    public List<T> getSuccessTasks() {
        return successTasks;
    }

    public void setSuccessTasks(List<T> successTasks) {
        this.successTasks = successTasks;
    }

    public List<T> getFailTasks() {
        return failTasks;
    }

    public void setFailTasks(List<T> failTasks) {
        this.failTasks = failTasks;
    }

    public Map<Integer, Integer> getRetryTasks() {
        return retryTasks;
    }

    public void setRetryTasks(Map<Integer, Integer> retryTasks) {
        this.retryTasks = retryTasks;
    }

    public LinkedList<Integer> getExecuteTrace() {
        return executeTrace;
    }

    public void setExecuteTrace(LinkedList<Integer> executeTrace) {
        this.executeTrace = executeTrace;
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

    public IRename getRenameStrategy() {
        return renameStrategy;
    }

    public void setRenameStrategy(IRename renameStrategy) {
        this.renameStrategy = renameStrategy;
    }

    public IFailStrategy getErrorStrategy() {
        return errorStrategy;
    }

    public void setErrorStrategy(IFailStrategy errorStrategy) {
        this.errorStrategy = errorStrategy;
    }

    public AtomicInteger getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(AtomicInteger retryCount) {
        this.retryCount = retryCount;
    }

    public AtomicInteger getNextTaskID() {
        return nextTaskID;
    }

    public void setNextTaskID(AtomicInteger nextTaskID) {
        this.nextTaskID = nextTaskID;
    }
}
