package org.example.designs.aop;



import lombok.RequiredArgsConstructor;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;



import java.lang.reflect.Method;
import java.util.Date;

@Component
@Aspect   //切面类
@RequiredArgsConstructor
public class SysAspect {

//    @Pointcut("@annotation(io.swagger.annotations.ApiOperation)")
//    private void pointcut() {
//
//    }
    @Pointcut("@annotation(org.example.designs.aop.Function)")
    private void pointcut() {

    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint joinPoint)  {
        System.out.println("123");
        // 获取方法签名
        String methodName = joinPoint.getSignature().getName();

        // 获取方法参数列表
        Object[] args = joinPoint.getArgs();
        System.out.println(args.toString());
        Object obj = null;
        try {
            obj = joinPoint.proceed();
        } catch (Throwable e) {

        }


        return obj;
    }


}
