package org.example.designs.business_flow;

import org.example.designs.business_flow.core.BusinessFlow;
import org.example.designs.business_flow.core.BusinessFlowException;

/**
 * TODO
 *
 * <p>
 *     TODO
 * </p>
 *
 * @author HaiYu
 * @date 2024-11-25@version 1.0.0
 */


public class TestController {


    public String test1() {
        BusinessFlow flow = null;
        try {
            flow = BusinessFlow
                    //整个业务流的描述
                    .build("数电红字确认单申请开具")
                    //add参数：方法提供bean（业务处理者），方法名（处理方法），描述，方法返回值key
                    .add(VtChBillService.class, "converDto"
                            , "Dto转销项开票明细", "vtChBill")
                    .add(VtChBillService.class, "converRedApply"
                            , "数电红字确认单申请主表", "redApply")
                    .add(VtChBillService.class, "converRedApply_B"
                            , "数电红字确认单申请明细", "redApply_bList")
                    .add(VtChBillService.class, "combine"
                            , "数电红字确认单申请主子表合并", "redApplyAsso")
                    .add(AelecbillRedapplyService.class, "redBill_upLoad"
                            , "上传红字确认单", "isSuccess")
                    //启动业务流，并输入此业务流的返回类型
                    .start();
            System.out.println(flow.getInfoLog());
            return "失败，失败原因是：...";
        } catch (BusinessFlowException e) {
            System.out.println(flow.getVisualLog());
            e.printStackTrace();
            return "失败，异常是：...";
        }
    }

    public String test() {
        BusinessFlow flow = null;
        try {
            flow = BusinessFlow
                    //整个业务流的描述
                    .build("数电红字确认单申请开具");
            flow
                    //add参数：方法提供bean（业务处理者），方法名（处理方法），描述，方法返回值key
                    .add(VtChBillService.class, "converDto")
                    .add(VtChBillService.class, "converRedApply")
                    .add(VtChBillService.class, "converRedApply_B")
                    .add(VtChBillService.class, "combine")
                    .add(AelecbillRedapplyService.class, "redBill_upLoad")
                    //启动业务流，并输入此业务流的返回类型
                    .start();
            System.out.println(flow.getVisualLog());
            return "成功，红字确认单申请开具成功";
        } catch (BusinessFlowException e) {
            e.printStackTrace();
            System.out.println(flow.getVisualLog());

            return "失败，异常是：...";
        }
    }
}
