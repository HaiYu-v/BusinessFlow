package org.example.designs.taskCollection.strategy.after.fail;

import org.example.designs.task.AbstractTask;
import org.example.designs.theadPool.TaskEnum;

/**
 * @Program: 0.test
 * @Author: 海里的鱼
 * @Create: 2024-08-26 20:46
 * @Name: TODO
 * @Desc: TODO
 */
public class RetryAndCollectFail implements IFailStrategy {
    //重试次数
    private int retryCount = 3;
    //间隔时间
    private Long interval = 100L;


    public RetryAndCollectFail() {

    }

    public RetryAndCollectFail(int retryCount) {
        this.retryCount = retryCount;
    }


    public RetryAndCollectFail(int retryCount, Long interval) {
        this.retryCount = retryCount;
        this.interval = interval;
    }

    @Override
    public void processError(AbstractTask task) {
        for(int i=0; task.getState()!= TaskEnum.SUCCESS && i<retryCount; i++){
            //暂停间隔
            try {
                Thread.sleep(interval);
                handler.addRetryCount();
                handler.errorRetry(task);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        if(task.getState() != TaskEnum.SUCCESS){
            handler.addErrorTask(task.getName());
        }
    }
}
