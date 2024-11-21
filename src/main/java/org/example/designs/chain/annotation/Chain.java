package org.example.designs.chain.annotation;

import java.lang.annotation.*;

@Target({ ElementType.PARAMETER, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Chain {
    //业务点描述
    String value();
    //业务点code
    String code() default "";
}
