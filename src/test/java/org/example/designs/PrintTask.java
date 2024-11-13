package org.example.designs;



import org.example.designs.task.AbstractTask;

import java.util.ArrayList;
import java.util.List;


/**
 *  继承BulkTask这个抽象类，来自定义任务和任务的执行逻辑
 */
class PrintTask extends AbstractTask {

    private int start;
    private int end;

    public PrintTask(int start, int end) {
        this.start = start;
        this.end = end;
    }

    /** ---------------------------------------------------------------------------------------------------------------------
     * @Method  : 打印[start,end]的所有数
     * @Describe: 重写这个方法，实现任务的执行逻辑，
     **/
    @Override
    public boolean executeFunction()  {
        List<Integer> list = new ArrayList<Integer>();
        for (int i = start; i <= end; i++) {
            list.add(i);
            System.out.print(i+",");
        }
        System.out.println(list);
        int x = 1/0;
        return  true;
    }
}

