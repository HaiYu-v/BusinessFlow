package org.example.designs.task;




import lombok.extern.slf4j.Slf4j;
import org.example.designs.task.strategy.after.IAfterExecute;
import org.example.designs.task.strategy.after.fail.Throw;
import org.example.designs.task.strategy.before.IBeforeExecute;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 任务抽象类
 *
 * <p>
 *     TODO
 * </p>
 *
 * @author HaiYu
 * @version 1.0.0
 * @date 2024-11-22
 */
@Slf4j
public abstract class AbstractTask {
    //ID自增，保证全局唯一
    private static AtomicInteger NextId  = new AtomicInteger(0);
    protected TaskInfo taskInfo;

//    //任务ID，唯一
//    protected Integer id;
//    //任务名
//    protected String name;
//    //优先级
//    protected Integer sort;
//    //任务的描述
//    protected String desc;
//    //执行次数
//    protected AtomicInteger countExecute = new AtomicInteger(0);
//    //任务状态
//    protected TaskStatusEnum state = TaskStatusEnum.UN_EXECUTED;
//    //开始时间
//    protected LocalDateTime startTime;
//    //结束时间
//    protected LocalDateTime endTime;

    //执行前的操作
    protected IBeforeExecute beforeExecute;
    //执行后的操作
    protected IAfterExecute afterExecute = new Throw();


    public AbstractTask() {
        this.taskInfo = new TaskInfo();
        taskInfo.countExecute = new AtomicInteger(0);
        taskInfo.state = TaskStatusEnum.UN_EXECUTED;
        taskInfo.id = NextId.incrementAndGet();
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * 任务执行函数
     *
     * 由子类自定义
     *
     * @param params
     * @return boolean
     * @throws Exception
     */
    public abstract boolean executeFunction(Map<String,Object> params) throws Exception;


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * 简单执行一次（无参）
     *
     * @return boolean
     * @throws TaskException
     */
    public boolean simpleExecute() throws TaskException {
        return simpleExecute(null);
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * 简单执行一次
     *
     * @param params
     * @return boolean
     * @throws TaskException
     */
    public boolean simpleExecute(Map<String,Object> params) throws TaskException {
        //修改状态为"任务开始"
        taskInfo.state= TaskStatusEnum.EXECUTING;
        try {
            taskInfo.countExecute.incrementAndGet();
            //执行任务
            if(executeFunction(params)){
                taskInfo.state = TaskStatusEnum.SUCCESS;
            }else {
                taskInfo.state = TaskStatusEnum.FAILED;
            }
        } catch (Exception e1) {
            taskInfo.state = TaskStatusEnum.EXCEPTION;
            throw new TaskException(e1);
        }
        return true;
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * 执行任务（无参）
     *
     * @return boolean
     * @throws TaskException
     */
    public boolean execute() throws TaskException {
         return execute(null);
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * 执行任务
     *
     * @return boolean
     * @throws TaskException
     */
    public boolean execute(Map<String,Object> params) throws TaskException {
        //执行的初始时间
        taskInfo.startTime = LocalDateTime.now();
        List<String> msg = new ArrayList<>();

        //任务执行前的操作
        Exception beforeException = null;
        try {
            if(beforeExecute != null)  beforeExecute.beforeExecute(this);
        } catch (Exception ex) {
//            log.error(ex.getMessage(),ex);
            beforeException = ex;
        }

        //任务执行
        Exception e = null;
        try {
            simpleExecute(params);
        } catch (Exception ex) {
//            log.error(ex.getMessage(),ex);
            e = ex;
        }

        //任务执行后的逻辑, 处理后是否还要抛出异常
        boolean isThrow = true;
        Exception aftereException = null;
        try {
           if (null != afterExecute) isThrow = afterExecute.afterExecute(this,e);
        } catch (Exception ex) {
//            log.error(ex.getMessage(),ex);
           aftereException = ex;
        }

        //任务结束时间
        taskInfo.endTime = LocalDateTime.now();

        //判断是否需要抛出异常
        if(e != null && isThrow){
            throw new TaskException(e);
        }else if(beforeException != null && aftereException != null){
            throw new TaskException((taskInfo.id!=null ? "Task:"+taskInfo.id+", " : "")+"beforeExecute and afterExecute error");
        }else if(beforeException != null){
            throw new TaskException((taskInfo.id!=null ? "Task:"+taskInfo.id+", " : "")+"beforeExecute error");
        }else if(aftereException != null){
            throw new TaskException((taskInfo.id!=null ? "Task:"+taskInfo.id+", " : "" )+"afterExecute error");
        }

        return taskInfo.state == TaskStatusEnum.SUCCESS;
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * 运行时间
     *
     * @return {@link Long }
     */
    public Long getRunningTime(){
        if(taskInfo.startTime == null || taskInfo.endTime == null){
            return -1L;
        }
        return ChronoUnit.MILLIS.between(taskInfo.startTime, taskInfo.endTime);
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * 任务信息
     *
     * @return {@link TaskInfo }
     */
    public TaskInfo getInfo(){
         return taskInfo;
    }


    public IBeforeExecute getBeforeExecute() {
        return beforeExecute;
    }

    public void setBeforeExecute(IBeforeExecute beforeExecute) {
        this.beforeExecute = beforeExecute;
    }

    public IAfterExecute getAfterExecute() {
        return afterExecute;
    }

    public void setAfterExecute(IAfterExecute afterExecute) {
        this.afterExecute = afterExecute;
    }

    public TaskStatusEnum getState() {
        return taskInfo.state;
    }

    public Integer getId() {
        return taskInfo.id;
    }

    public void setId(Integer id) {
        taskInfo.id = id;
    }

    public String getName() {
        return taskInfo.name;
    }

    public void setName(String name) {
        taskInfo.name = name;
    }

    public Integer getSort() {
        return taskInfo.sort;
    }

    public void setSort(Integer sort) {
        taskInfo.sort = sort;
    }

    public String getDesc() {
        return taskInfo.desc;
    }

    public void setDesc(String desc) {
        taskInfo.desc = desc;
    }

    public AtomicInteger getCountExecute() {
        return taskInfo.countExecute;
    }

    public void setCountExecute(AtomicInteger countExecute) {
        taskInfo.countExecute = countExecute;
    }

    public void setState(TaskStatusEnum state) {
        taskInfo.state = state;
    }

    public LocalDateTime getStartTime() {
        return taskInfo.startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        taskInfo.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return taskInfo.endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        taskInfo.endTime = endTime;
    }
}

