package org.example.designs.task.strategy.after.fail;

import lombok.extern.slf4j.Slf4j;
import org.example.designs.task.AbstractTask;
import org.example.designs.task.TaskEnum;
import org.example.designs.task.strategy.after.IAfterExecute;



/**
 * @Program: 0.test
 * @Author: 海里的鱼
 * @Create: 2024-08-26 15:22
 * @Name: 重新执行任务策略
 * @Desc: 任务失败重试
 */
@Slf4j
public class RetryFail implements IAfterExecute {

    //重试次数
    private int retryCount = 2;
    //间隔时间
    private Long interval = 100L;

    public RetryFail() {

    }

    public RetryFail(int retryCount) {
        this.retryCount = retryCount;
    }


    public RetryFail(int retryCount, Long interval) {
        this.retryCount = retryCount;
        this.interval = interval;
    }

    @Override
    public boolean afterExecute(AbstractTask task, Exception e) {
        int count = retryCount;
        while(task.getState() != TaskEnum.SUCCESS && count > 0){
            try {
                Thread.sleep(interval);
                count--;
                task.simpleExecute();
            } catch (Exception ex) {
                log.error(ex.getMessage(),ex);
            }
        }
        return task.getState() != TaskEnum.SUCCESS;
    }
}
