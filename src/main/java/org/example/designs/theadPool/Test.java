//package com.mm.common.designs.theadPool;
//
//
//
//
//import com.mm.common.designs.theadPool.strategy.error.RetryAndCollectError;
//import com.mm.common.designs.theadPool.strategy.name.AutoIncrementRename;
//
//import java.util.ArrayList;
//import java.util.List;
//
//
//
///**
// *  继承BulkTask这个抽象类，来自定义任务和任务的执行逻辑
// */
//class PrintTask extends BulkTask {
//
//    private int start;
//    private int end;
//
//    public PrintTask(int start, int end) {
//        this.start = start;
//        this.end = end;
//    }
//
//    /** ---------------------------------------------------------------------------------------------------------------------
//     * @Method  : 打印[start,end]的所有数
//     * @Describe: 重写这个方法，实现任务的执行逻辑，
//     **/
//    @Override
//    public boolean executeFunction(){
////        //报错,让任务执行失败
////        int n = 1/0;
//
//        List<Integer> list = new ArrayList<Integer>();
//        for (int i = start; i <= end; i++) {
//            list.add(i);
//            System.out.print(i+",");
//        }
//        System.out.println();
//        return  true;
//    }
//}
//
//
//
//public class Test {
//    public static void main(String[] args) {
//
//        //1.创建一个任务集合，并且添加任务
//        BulkTaskCollection<PrintTask> taskList = BulkExecuteUtil.getTaskCollection(PrintTask.class);
//
//        //2.获取一个任务集合的执行者，将任务集合作为参数
//        BulkExecuteHandler<PrintTask> handler = BulkExecuteUtil.getHandler(taskList);
//
//        /* 3.设置【任务命名】策略，可以自定义，实现对应的接口就行
//         * 任务命名的策略：数字自增，UUID，自命名（默认）
//         */
//
//        //设置【数字自增】策略（start 起始数字，prefix 名称前缀）
//        taskList.setRenameStrategy(new AutoIncrementRename(1,"Task:"));
////        //设置【UUID】策略（prefix 前缀）
////        taskList.setRenameStrategy(new UUIDRename("任务："));
////        //设置【自命名】策略（无参数），任务名通过调用任务的setName方法来自定义
////        taskList.setRenameStrategy(new SelfRename());
//
//
//
//        /* 4.设置【任务失败处理】策略，可以自定义，实现对应的接口就行
//         * 任务执行失败的处理策略：不处理，重试，记录失败任务（默认），重试且记录失败任务
//         */
//
//        //设置【重试且记录失败任务】策略（retryCount 尝试重试的次数，默认为3；interval 每次任务执行的间隔时间，默认为100毫秒）
//        handler.setErrorStrategy(new RetryAndCollectError(3,100L));
////        //设置【不处理】策略（无参数）
////        handler.setErrorStrategy(new NoProcessError());
////        //设置【重试】策略（retryCount 尝试重试的次数，默认为3；interval 每次任务执行的间隔时间，默认为100毫秒）
////        handler.setErrorStrategy(new RetryError(3,100L));
////        //设置【记录失败任务】策略（无参数）
////        handler.setErrorStrategy(new CollectError());
//
//        //添加任务
//        taskList.add(new PrintTask(1,100000));     //打印1~100000
//        taskList.add(new PrintTask(100001,200000));
//        taskList.add(new PrintTask(200001,300000));
//        taskList.add(new PrintTask(300001,400000));
//        taskList.add(new PrintTask(400001,500000));
//
//        //5.执行任务集合，线程池的参数可以在“ThreadPool”类中调整
//        try {
//            handler.executeTasks();
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//
//        System.out.println("\n任务集执行结果---------------------------");
//        //6.获取并且打印执行结果
//        System.out.println(handler.getExecuteInfo());
//
//        /*
//         * success=5, 任务执行成功数
//         * error=0, 任务执行失败数
//         * retryCount=0, 所有任务的失败重试次数
//         * count=5, 任务总数
//         * startTime=2024-08-27 20:36:01, 任务集执行起始时间
//         * endTime=2024-08-27 20:36:02, 任务集执行结束时间
//         * runningTime=189，任务执行总用时，单位毫秒
//         */
//
//        System.out.println("\n所有任务的状态---------------------------");
//        //7.打印所有任务的状态
//        for (BulkTask task : taskList.getTasks()) {
//            System.out.println(task.getTaskInfo());
//        }
//
//        /*
//         * name=任务：1, 任务名
//         * state=执行成功, 任务状态
//         * startTime=2024-08-27 20:36:01, 此任务执行的起始时间
//         * endTime=2024-08-27 20:36:02， 此任务执行结束时间
//         * runningTime=157，此任务执行用时，单位毫秒
//         */
//
//        System.out.println("\n失败任务-------------------------------");
//        //8. 获取并且打印失败任务名
//        for(Object name : handler.getErrorTasks()){
//            System.out.println(name);
//        }
//
//        System.out.println("\n执行单个任务----------------------------");
//        //9.执行单个任务
//        try {
//            handler.executeTask(new PrintTask(1,10));
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//        System.out.println(handler.getExecuteInfo());
//    }
//}