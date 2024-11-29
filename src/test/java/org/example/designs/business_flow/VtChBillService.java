package org.example.designs.business_flow;

import org.apache.ibatis.annotations.Select;
import org.example.designs.business_flow.annotation.Chain;
import org.springframework.stereotype.Service;

/**
 * TODO
 *
 * <p>
 *     TODO
 * </p>
 *
 * @author HaiYu
 * @version 1.0.0
 * @date 2024-11-25
 */
@Service
public class VtChBillService {

    @Chain(desc = "Dto转销项开票明细",retCode = "vtChBill")
    public int converDto() throws InterruptedException {
        Thread.sleep(300);
        System.out.println("converDto");
        return 1;
    }

    @Chain(desc = "数电红字确认单申请主表",retCode = "redApply")
    public int converRedApply(int vtChBill) throws InterruptedException {
//        throw new InterruptedException("任务失败");
        Thread.sleep(300);
        System.out.println("converRedApply");
        System.out.println(vtChBill);
        return 2;
    }

    @Chain(desc = "数电红字确认单申请明细",retCode = "redApply_bList")
    public void converRedApply_B() throws InterruptedException {
        Thread.sleep(300);
        System.out.println("converRedApply_B");

    }

    @Chain(desc = "数电红字确认单申请主子表合并",retCode = "redApplyAsso")
    public void combine() throws InterruptedException {
        Thread.sleep(300);
        System.out.println("combine");

    }
}
