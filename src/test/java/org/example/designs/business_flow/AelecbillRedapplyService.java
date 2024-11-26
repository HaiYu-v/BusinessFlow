package org.example.designs.business_flow;

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
public class AelecbillRedapplyService {

    @Chain(desc = "Dto转销项开票明细", retCode = "redBill_upLoad")
    public void redBill_upLoad() throws InterruptedException {
        Thread.sleep(300);
        System.out.println("redBill_upLoad");

    }
}
