package org.example.designs.chain.annotation;

import java.lang.annotation.*;

@Target({ ElementType.PARAMETER, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Table {
    String value();
}
