package org.example.designs;

import org.example.designs.business_flow.AelecbillRedapplyService;
import org.example.designs.business_flow.VtChBillService;
import org.example.designs.business_flow.core.BusinessFlow;
import org.example.designs.business_flow.core.BusinessFlowException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


public class TestController {


    public String test1(){
        try {
            BusinessFlow flow = BusinessFlow
                    //整个业务流的描述
                    .build("数电红字确认单申请开具");

            boolean isSuccess = flow
                    //add参数：方法提供bean（业务处理者），方法名（处理方法），描述，方法返回值key
                    .add(VtChBillService.class,"converDto"
                            ,"Dto转销项开票明细","vtChBill")
                    .add(VtChBillService.class,"converRedApply"
                            ,"数电红字确认单申请主表","redApply")
                    .add(VtChBillService.class,"converRedApply_B"
                            ,"数电红字确认单申请明细","redApply_bList")
                    .add(VtChBillService.class,"combine"
                            ,"数电红字确认单申请主子表合并","redApplyAsso")
                    .add(AelecbillRedapplyService.class,"redBill_upLoad"
                            ,"上传红字确认单","isSuccess")
                    //启动业务流，并输入此业务流的返回类型
                    .start(Boolean.class);
            System.out.println(flow.getInfoJSON());
            if(isSuccess){
                return "成功，红字确认单申请开具成功";
            }
            return "失败，失败原因是：...";
        } catch (BusinessFlowException e) {
            e.printStackTrace();
            return "失败，异常是：...";
        }
    }

    public String test(){
        try {
            BusinessFlow flow = BusinessFlow
                    //整个业务流的描述
                    .build("数电红字确认单申请开具");
            flow
                    //add参数：方法提供bean（业务处理者），方法名（处理方法），描述，方法返回值key
                    .add(VtChBillService.class,"converDto")
                    .add(VtChBillService.class,"converRedApply")
                    .add(VtChBillService.class,"converRedApply_B")
                    .add(VtChBillService.class,"combine")
                    .add(AelecbillRedapplyService.class,"redBill_upLoad")
                    //启动业务流，并输入此业务流的返回类型
                    .start();
            System.out.println(flow.getVisualJSON());
            return "成功，红字确认单申请开具成功";
        } catch (BusinessFlowException e) {
            e.printStackTrace();
            return "失败，异常是：...";
        }
    }
}
