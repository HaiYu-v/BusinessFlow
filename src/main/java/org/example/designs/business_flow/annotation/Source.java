package org.example.designs.business_flow.annotation;

import org.example.designs.business_flow.cache.GlobalCache;

import java.lang.annotation.*;

/**
 * 来源注解
 *
 * <p>
 *     1.根据value值，从数据源中自动匹配数据；无value，则根据参数名匹配
 *     2.能从{@link GlobalCache}中自动匹配
 * </p>
 *
 * @author HaiYu
 * @version 1.0.0
 * @date 2024-11-22
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Source {
    String value() default "";
}
