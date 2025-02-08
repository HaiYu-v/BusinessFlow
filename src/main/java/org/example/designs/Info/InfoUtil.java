package org.example.designs.Info;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 信息缓存工具
 *
 * <p>
 *     desc为key，每个desc都有其单独的日志记录
 *     1.计时器
 *     2.计数器
 *     3.信息
 * </p>
 *
 * @author HaiYu
 * @version 1.0.0
 * @date 2025-02-08
 */
public class InfoUtil {
    //线程安全的hashMap
    private static ConcurrentHashMap<String, InfoCache> infoMap = new ConcurrentHashMap<>();
    //时间格式
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static InfoCache build(String desc){
        return new InfoCache(desc,formatter);
    }
    
    /**
     * -----------------------------------------------------------------------------------------------------------------
     * 开始计时
     *
     * @param desc
     * @return long
     */
    public static long start(String desc){
        InfoCache info = check(desc);
        return info.start();
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * 计时结束
     *
     * @param desc
     * @return {@link Long }
     */
    public static Long end(String desc){
        InfoCache info = check(desc);
        return info.end();
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * 计数
     *
     * @param desc
     * @return int
     */
    public static int count(String desc){
        InfoCache info = check(desc);
        return info.count();
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * 设置信息
     *
     * @param desc
     * @param key
     * @param value
     * @return {@link Object }
     */
    public static Object putInfo(String desc,String key,Object value){
        InfoCache info = check(desc);
        return info.putInfo(key, value);
    }
    //map
    public static Object putInfo(String desc,Map<String,Object> infoMap){
        InfoCache info = check(desc);
        return info.putInfo(infoMap);
    }

    //bean
    public static Object putInfo(String desc,Object object){
        InfoCache info = check(desc);
        return info.putInfo(object);
    }


    public static Object get(String desc,String key){
        InfoCache info = check(desc);
        return info.get(key);
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * 获取信息
     *
     * @param desc
     * @return {@link String }
     */
    public static String infoJson(String desc){
        InfoCache info = check(desc);
        return info.infoJson();
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * 获取信息
     *
     * @param desc
     * @return {@link String }
     */
    public static String infoPrettyStr(String desc){
        InfoCache info = check(desc);
        return info.infoPrettyStr();
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * 检查desc是否有对应的info
     *
     * @param desc
     * @return {@link Map }<{@link String }, {@link Object }>
     */
    private static InfoCache check(String desc){
        InfoCache info = infoMap.get(desc);
        if(null == infoMap.get(desc)){
            info = build(desc);
            infoMap.put(desc, info);
        }
        return info;
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * 设置日期格式
     *
     * @param formatter
     */
    public static void setFormatter(String formatter) {
        InfoUtil.formatter = DateTimeFormatter.ofPattern(formatter);
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * 获取一些常见属性
     *
     * @param desc
     * @return {@link String }
     */
    public static String getDesc(String desc){
        InfoCache info = check(desc);
        return info.getDesc();
    }

    public static String getStart(String desc){
        InfoCache info = check(desc);
        return info.getStart();
    }
    public static LocalDateTime getStartDateTime(String desc){
        InfoCache info = check(desc);
        return info.getStartDateTime();
    }
    public static Long getStartTimestamp(String desc){
        InfoCache info = check(desc);
        return info.getStartTimestamp();
    }

    public static Long getRunningTime(String desc){
        InfoCache info = check(desc);
        return info.getRunningTime();
    }

    public static Integer getCount(String desc){
        InfoCache info = check(desc);
        return info.getCount();
    }

    public static String getStr(String desc,String key){
        InfoCache info = check(desc);
        return info.getStr(key);
    }
}
