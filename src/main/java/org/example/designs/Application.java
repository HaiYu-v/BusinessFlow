package org.example.designs;

import org.example.aop.Function;
import org.example.designs.utils.beanUtils.BeanCopyUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.lang.reflect.InvocationTargetException;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(Application.class, args);
        run.getBean(CopyTest.class).func();



    }



}
