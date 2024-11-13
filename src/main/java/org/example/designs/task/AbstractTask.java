package org.example.designs.task;




import lombok.extern.slf4j.Slf4j;
import org.example.designs.task.strategy.after.IAfterExecute;
import org.example.designs.task.strategy.after.fail.Throw;
import org.example.designs.task.strategy.before.IBeforeExecute;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: 海里的鱼
 * @Create: 2024-08-26 09:22
 * @Name: 任务的抽象类类
 * @Desc: TODO
 */
@Slf4j
public abstract class AbstractTask {
    //ID自增
    private static AtomicInteger NextId  = new AtomicInteger(0);
    //任务ID，唯一
    protected Integer id;
    //任务名
    protected String name;
    //优先级
    protected Integer sort;
    //任务的描述
    protected String describe;
    //执行次数
    protected AtomicInteger countExecute = new AtomicInteger(0);
    //任务状态
    protected TaskEnum state = TaskEnum.UN_EXECUTED;
    //开始时间
    protected LocalDateTime startTime;
    //结束时间
    protected LocalDateTime endTime;
    //执行后的消息
    protected String msg;
    //执行前的操作
    protected IBeforeExecute beforeExecute;
    //执行后的操作
    protected IAfterExecute afterExecute = new Throw();


    public AbstractTask() {
        this.id = NextId.incrementAndGet();
    }

    //自定义，任务执行函数
    public abstract boolean executeFunction() throws Exception;

    /** ---------------------------------------------------------------------------------------------------------------------
     * @Method  : 简单的执行任务一次
     * @Describe: TODO
     **/
     public void simpleExecute() throws Exception {
        //修改状态为"任务开始"
        state= TaskEnum.EXECUTING;
        try {
            countExecute.incrementAndGet();
            //执行任务
            if(executeFunction()){
                state = TaskEnum.SUCCESS;
            }else {
                state = TaskEnum.FAILED;
            }
        } catch (Exception e1) {
            state = TaskEnum.EXCEPTION;
            throw e1;
        }
    }

    /** ---------------------------------------------------------------------------------------------------------------------
     * @Method  : 执行任务
     * @Describe: TODO
     **/
     public boolean execute() throws Exception {
        //执行的初始时间
        startTime = LocalDateTime.now();
        List<String> msg = new ArrayList<>();

        //任务执行前的操作
        Exception beforeException = null;
        try {
            if(beforeExecute != null)  beforeExecute.beforeExecute(this);
        } catch (Exception ex) {
            log.error(ex.getMessage(),ex);
            beforeException = ex;
        }

        //任务执行
        Exception e = null;
        try {
            simpleExecute();
        } catch (Exception ex) {
            log.error(ex.getMessage(),ex);
            e = ex;
        }

        //任务执行后的逻辑, 处理后是否还要抛出异常
        boolean isThrow = true;
        Exception aftereException = null;
        try {
           if (null != afterExecute) isThrow = afterExecute.afterExecute(this,e);
        } catch (Exception ex) {
            log.error(ex.getMessage(),ex);
           aftereException = ex;
        }

        //任务结束时间
        endTime = LocalDateTime.now();

        //判断是否需要抛出异常
        if(e != null && isThrow){
            throw e;
        }else if(beforeException != null && aftereException != null){
            throw new TaskException((id!=null ? "Task:"+id+", " : "")+"beforeExecute and afterExecute error");
        }else if(beforeException != null){
            throw new TaskException((id!=null ? "Task:"+id+", " : "")+"beforeExecute error");
        }else if(aftereException != null){
            throw new TaskException((id!=null ? "Task:"+id+", " : "" )+"afterExecute error");
        }

        return state == TaskEnum.SUCCESS;
    }


    /** ---------------------------------------------------------------------------------------------------------------------
     * @Method  : 获取运行时间
     * @Describe: TODO
     **/
     public Long getRunningTime(){
        if(startTime == null || endTime == null){
            return -1L;
        }
        return ChronoUnit.MILLIS.between(startTime, endTime);
    }

    /** ---------------------------------------------------------------------------------------------------------------------
     * @Method  : 获取任务的信息
     * @Describe: TODO
     **/
     public TaskInfo getInfo(){
         TaskInfo taskInfo = new TaskInfo();
         taskInfo.setId(id);
         taskInfo.setName(name);
         taskInfo.setState(state);
         taskInfo.setSort(sort);
         taskInfo.setStartTime(startTime);
         taskInfo.setEndTime(endTime);
         taskInfo.setCountExecute(countExecute);
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

    public TaskEnum getState() {
        return state;
    }

    public void setState(TaskEnum state) {
        this.state = state;
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


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public AtomicInteger getCountExecute() {
        return countExecute;
    }

    public void setCountExecute(AtomicInteger countExecute) {
        this.countExecute = countExecute;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


}

