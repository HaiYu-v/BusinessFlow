package org.example.designs.theadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Program: 0.test
 * @Author: 海里的鱼
 * @Create: 2024-08-26 15:57
 * @Name: 批量任务的执行线程池
 * @Desc: TODO
 */
public class BulkThreadPool {

    /**
     * 核心线程池大小
     */
    private static final int CORE_POOL_SIZE = 5;

    /**
     * 最大可创建的线程数
     */
    private static final int MAX_POOL_SIZE = 50;

    /**
     * 队列最大长度
     */
    private static final int QUEUE_CAPACITY = 1000;

    /**
     * 线程池维护线程所允许的空闲时间
     */
    private static final int KEEP_ALIVE_SECONDS = 500;


    /** ---------------------------------------------------------------------------------------------------------------------
     * @Method  : 获取线程池对象
     * @Describe: TODO
     **/
     public static ExecutorService executorService(){
        AtomicInteger c = new AtomicInteger(1);
        LinkedBlockingQueue<Runnable> queue = new LinkedBlockingQueue<Runnable>(QUEUE_CAPACITY);
        return new ThreadPoolExecutor(
                CORE_POOL_SIZE,
                MAX_POOL_SIZE,
                KEEP_ALIVE_SECONDS,
                TimeUnit.MILLISECONDS,
                queue,
                r -> new Thread(r, "mindmap-pool-" + c.getAndIncrement()),
                new ThreadPoolExecutor.AbortPolicy()
        );
    }
}
