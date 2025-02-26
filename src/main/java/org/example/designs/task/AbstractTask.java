package org.example.designs.task;




import lombok.extern.slf4j.Slf4j;
import org.example.designs.Info.InfoCache;
import org.example.designs.task.postProcessor.after.IAfterPostProcessor;
import org.example.designs.task.postProcessor.after.fail.Throw;
import org.example.designs.task.postProcessor.before.IBeforePostProcessor;

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
    public static String EXECUTE_COUNT = "executeCount";
    //ID自增，保证全局唯一
    private static AtomicInteger NextId  = new AtomicInteger(0);
    //执行前的操作
    protected IBeforePostProcessor beforeExecute;
    //执行后的操作
    protected IAfterPostProcessor afterExecute = new Throw();
    //任务信息
    protected InfoCache info;


    public AbstractTask(String desc) {
        this.info = InfoCache.build(desc);
        this.info.putInfo("id",NextId.incrementAndGet());
        this.info.nothing();
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
        this.info.startTime();
        List<String> msg = new ArrayList<>();

        //任务执行前的操作
        Exception beforeException = null;
        try {
            if(beforeExecute != null)  beforeExecute.beforeExecute(this);
        } catch (Exception ex) {
            beforeException = ex;
        }

        //任务执行
        Exception e = null;
        try {
            //修改状态为"任务开始"
            if(!executeFunction(params)) this.info.failed();
        } catch (Exception ex) {
            this.info.exceptional();
            e = ex;
        }

        //任务执行后的逻辑, 处理后是否还要抛出异常
        boolean isThrow = true;
        Exception aftereException = null;
        try {
           if (null != afterExecute) isThrow = afterExecute.afterExecute(this,e);
        } catch (Exception ex) {
           aftereException = ex;
        }

        //任务结束时间

        info.endTime();

        //判断是否需要抛出异常
        if(e != null && isThrow){
            throw new TaskException(String.format("任务[%s]执行异常",info.getDesc()),e);
        }else if(beforeException != null && aftereException != null){
            throw new TaskException(String.format("任务[%s]前置和后置逻辑执行失败",info.getDesc()));
        }else if(beforeException != null){
            throw new TaskException(String.format("任务[%s]后置逻辑执行失败",info.getDesc()));
        }else if(aftereException != null){
            throw new TaskException(String.format("任务[%s]前置逻辑执行失败",info.getDesc()));
        }

        return info.success();
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * 运行时间
     *
     * @return {@link Long }
     */
    public Long getRunningTime(){
        return info.getStartTimestamp();
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * 任务信息
     *
     * @return {@link TaskInfo }
     */
    public InfoCache getInfo(){
         return info;
    }


    public IBeforePostProcessor getBeforeExecute() {
        return beforeExecute;
    }

    public void setBeforeExecute(IBeforePostProcessor beforeExecute) {
        this.beforeExecute = beforeExecute;
    }

    public IAfterPostProcessor getAfterExecute() {
        return afterExecute;
    }

    public void setAfterExecute(IAfterPostProcessor afterExecute) {
        this.afterExecute = afterExecute;
    }

    public String getState() {
        return this.info.getState();
    }

    public String getId() {
        return info.getID();
    }

    public void setId(String id) {
        info.putInfo("id", id);
    }



    public String getDesc() {
        return info.getDesc();
    }

    public void setDesc(String desc) {
        info.putInfo("desc", desc);
    }

    public int getCountExecute() {
        return info.getCount(EXECUTE_COUNT);
    }


    public LocalDateTime getStartTime() {
        return info.getStartDateTime();
    }

    public void setStartTime(LocalDateTime startTime) {
        info.putInfo("startTime", startTime);
    }


}

