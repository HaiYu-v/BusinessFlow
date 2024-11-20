package org.example.designs.strategy;


import org.example.designs.utils.AppContextUtil;
import org.springframework.context.ApplicationContext;

/**
 * 策略工厂，用于获取策略对象
 * 从Spring容器中获取指定的bean
 */
public class StrategyFactory {
    //Spring容器，用于从容器中获取处理者的bean
    private static ApplicationContext applicationContext = AppContextUtil.getApplicationContext();

    //私有构造方法
    private StrategyFactory(){}

    /** ---------------------------------------------------------------------------------------------------------------------
     * @Method  : 获取容器对象，以获取Bean，在SpringBoot启动后记得配置一下
     * @Describe: TODO
     **/
    public static void setApplicationContext(ApplicationContext applicationContext) {
        StrategyFactory.applicationContext = applicationContext;
    }

    /** ---------------------------------------------------------------------------------------------------------------------
     * @Method  : 获取bean
     * @Describe: TODO
     **/
     public static <T extends IStrategy> T getStrategy(Class<T> strategy){
        return applicationContext.getBean(strategy);
    }
}
