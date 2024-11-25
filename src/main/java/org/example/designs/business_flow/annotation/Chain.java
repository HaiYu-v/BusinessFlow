package org.example.designs.business_flow.annotation;

import java.lang.annotation.*;

/**
 * 标注业务点
 *
 * <p>
 *     1.被标注的方法就是业务点的处理方法
 *     2.需要 业务点处理方法描述（必填），业务点code（默认方法名）
 * </p>
 *
 * @author HaiYu
 * @version 1.0.0
 * @date 2024-11-22
 */
@Target(ElementType.METHOD )
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Chain {
    //业务点处理方法描述
    String desc();
    //业务处理方法的返回值key，存入临时缓存的key
    String retCode();
    //业务点code
    String code() default "";
}
