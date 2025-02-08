package org.example.designs.Info;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 信息缓存
 *
 * <p>
 *     InfoUtil是static的，通过desc来相互隔离。
 *     如果担心出现问题，那就使用这个来记录日志，这是创建对象来使用的
 * </p>
 *
 * @author HaiYu
 * @version 1.0.0
 * @date 2025-02-08
 */
public class InfoCache {
    //Map
    private  Map<String, Object> info = new HashMap<>();
    //时间格式
    private  DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public InfoCache(String desc) {
        info.put("desc", desc);
    }
    public InfoCache(String desc, DateTimeFormatter format) {
        info.put("desc", desc);
        this.formatter = format;
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * 开始计时
     *
     * @return long
     */
    public long start(){
        long start = System.currentTimeMillis();
        info.put("startTime", start);
        return start;
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * 计时结束
     *
     * @return {@link Long }
     */
    public  Long end(){
        long end = System.currentTimeMillis();

        if(!info.containsKey("startTime") || null == info.get("startTime")){
            return null;
        }
        long startTime = (long) info.get("startTime");
        Long running = end - startTime;
        info.put("running", running);
        return running;
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * 计数
     *
     * @return int
     */
    public  int count(){
        AtomicInteger count = (AtomicInteger) info.getOrDefault("count", new AtomicInteger(0));
        count.incrementAndGet();
        info.put("count", count);
        return count.get();
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * 设置信息
     *
     * @param key
     * @param value
     * @return {@link Object }
     */
    public  Object putInfo(String key,Object value){
        return info.put(key, value);
    }

    //map
    public  Object putInfo(Map<String,Object> infoMap){
        info.putAll(infoMap);
        return info;
    }

    //bean
    public  Object putInfo(Object object){
        info.putAll(BeanUtil.beanToMap(object));
        return info;
    }


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * 获取信息
     *
     * @param key
     * @return {@link String }
     */
    public Object get(String key){
        return info.get(key);
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * JSON格式的信息
     *
     * @return {@link String }
     */
    public  String infoJson(){
        if(info.containsKey("startTime") && info.get("startTime") instanceof Long){
            info.put("startTime", LocalDateTime.ofInstant(
                            Instant.ofEpochMilli((long) info.get("startTime")), ZoneId.systemDefault())
                    .format(formatter));
        }
        return JSONUtil.toJsonStr(info);
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * 获取信息
     *
     * @return {@link String }
     */
    public  String infoPrettyStr(){
        if(info.containsKey("startTime") && info.get("startTime") instanceof Long){
            info.put("startTime", LocalDateTime.ofInstant(
                            Instant.ofEpochMilli((long) info.get("startTime")), ZoneId.systemDefault())
                    .format(formatter));
        }

        return JSONUtil.toJsonPrettyStr(info);
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * 设置日期格式
     *
     * @param formatter
     */
    public void setFormatter(String formatter) {
        this.formatter = DateTimeFormatter.ofPattern(formatter);
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * 获取一些常见属性
     *
     * @return {@link String }
     */
    public String getDesc(){
        Object object = info.get("desc");
        if(null == object || !(object instanceof String)) return null;
        return (String) object ;
    }

    public String getStart(){
        Object object = info.get("startTime");
        if(null == object || !(object instanceof String)) return null;
        return (String) object ;
    }
    public LocalDateTime getStartDateTime(){
        Object object = info.get("startTime");
        if(null == object || !(object instanceof String)) return null;
        return LocalDateTime.parse((String) object, formatter) ;
    }
    public Long getStartTimestamp(){
        Object object = info.get("startTime");
        if(null == object || !(object instanceof String)) return null;
        return LocalDateTime.parse((String) object, formatter).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli() ;
    }

    public Long getRunningTime(){
        Object object = info.get("running");
        if(null == object || !(object instanceof Long)) return null;
        return (Long) object ;
    }

    public Integer getCount(){
        Object object = info.get("count");
        if(null == object || !(object instanceof AtomicInteger)) return null;
        return ((AtomicInteger)object).get() ;
    }

    public String getStr(String key){
        Object object = info.get(key);
        if(null == object || !(object instanceof String)) return null;
        return (String) object ;
    }
}
