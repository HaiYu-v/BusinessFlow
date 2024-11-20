package org.example.designs.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Spring的Bean容器的工具类
 *
 * <p>
 *      TODO
 * </p>
 *
 * @author  HaiYu
 * @version 1.0.0
 * @create  2024-11-19
 */
@Slf4j
@Component
public class AppContextUtil implements ApplicationContextAware {
    // 定义静态ApplicationContext
    private static ApplicationContext applicationContext = null;

    /**
     * 重写接口的方法,该方法的参数为框架自动加载的IOC容器对象
     * 该方法在启动项目的时候会自动执行,前提是该类上有IOC相关注解,例如@Component
     *
     * @param applicationContext ioc容器
     * @throws BeansException e
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        // 将框架加载的ioc赋值给全局静态ioc
        AppContextUtil.applicationContext = applicationContext;
        log.info("\n==================ApplicationContext加载成功==================\n");
    }


    /** ---------------------------------------------------------------------------------------------------------------------
     * @Method  : 获取applicationContext
     * @Describe: TODO
     **/
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /** ---------------------------------------------------------------------------------------------------------------------
     * @Method  : 通过name获取 Bean.
     * @Describe: TODO
     **/
    public static Object getBean(String name) {
        return getApplicationContext().getBean(name);
    }

    /** ---------------------------------------------------------------------------------------------------------------------
     * @Method  : 通过class获取Bean.
     * @Describe: TODO
     **/
    public static <T> T getBean(Class<T> clazz) {
        return getApplicationContext().getBean(clazz);
    }

    /** ---------------------------------------------------------------------------------------------------------------------
     * @Method  : 通过name,以及Clazz返回指定的Bean
     * @Describe: TODO
     **/
     public static <T> T getBean(String name, Class<T> clazz) {
        return getApplicationContext().getBean(name, clazz);
    }

    /** ---------------------------------------------------------------------------------------------------------------------
     * 是否含有某个Bean
     *
     * @param  var1 Bean名
     * @return boolean 含有与否
     */
     public static boolean containsBean(String var1) {
        return getApplicationContext().containsBean(var1);
    }
}
