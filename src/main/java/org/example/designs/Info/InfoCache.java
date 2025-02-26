package org.example.designs.Info;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
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
    private final AtomicInteger NEXT_ID = new AtomicInteger(0);
    //TreeMap能进行正序的排序
    private  Map<String, Object> info = new TreeMap<>();
    //时间格式
    private  DateTimeFormatter formatter = DateTimeFormatter.ofPattern(InfoConstant.DateFormatter);


    private InfoCache(String desc, DateTimeFormatter format) {
        if(null != desc) this.info.put(InfoConstant.desc, desc);
        if(null != format ) this.formatter = format;
    }
    public static InfoCache build(){
        return new InfoCache(null,null);
    }
    public static InfoCache build(String desc){
        return new InfoCache(desc,null);
    }
    public static InfoCache build(String desc, DateTimeFormatter format){
        return new InfoCache(desc,format);
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * 开始计时
     *
     * @return long
     */
    public long startTime(){
        long start = System.currentTimeMillis();
        info.put(InfoConstant.startTime, start);
        return start;
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * 计时结束
     *
     * @return {@link Long }
     */
    public  Long endTime(){
        long end = System.currentTimeMillis();

        if(!info.containsKey(InfoConstant.startTime) || null == info.get(InfoConstant.startTime)){
            return null;
        }
        long startTime = (long) info.get(InfoConstant.startTime);
        Long running = end - startTime;
        info.put(InfoConstant.running, running);
        return running;
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * ID
     *
     * @return int
     */
    public String id(){
        String id = String.valueOf(NEXT_ID.incrementAndGet());
        info.put(InfoConstant.id, id);
        return id;
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * ID(UUID)
     *
     * @return int
     */
    public String idFromUUID(){
        String id = UUID.randomUUID().toString();
        info.put(InfoConstant.id, id);
        return id;
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * 计数
     *
     * @return int
     */
    public  int count(String key){
        AtomicInteger count = (AtomicInteger) info.getOrDefault(key, new AtomicInteger(0));
        count.incrementAndGet();
        info.put(key, count);
        return count.get();
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * 无事
     */
    public boolean nothing(){
        info.put(InfoConstant.state,InfoConstant.nothing_state);
        return true;
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * 失败
     */
    public boolean failed(){
//        String state = (String)info.getOrDefault(InfoConstant.state, InfoConstant.nothing_state);
//        if(!state.equals(InfoConstant.nothing_state)){
//            throw new RuntimeException(String.format("只有状态为【%S】时，才能设为【%S】",InfoConstant.nothing_state,InfoConstant.failed_state));
//        }
        info.put(InfoConstant.state,InfoConstant.failed_state);
        return false;
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * 异常
     */
    public boolean exceptional(){
//        String state = (String)info.getOrDefault(InfoConstant.state, InfoConstant.nothing_state);
//        if(!state.equals(InfoConstant.nothing_state)){
//            throw new RuntimeException(String.format("只有状态为【%S】时，才能设为【%S】",InfoConstant.nothing_state,InfoConstant.exceptional_state));
//        }
        info.put(InfoConstant.state,InfoConstant.exceptional_state);
        return false;
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * 进行中
     */
    public boolean executing(){
//        String state = (String)info.getOrDefault(InfoConstant.state, InfoConstant.nothing_state);
//        if(!state.equals(InfoConstant.nothing_state)){
//            throw new RuntimeException(String.format("只有状态为【%S】时，才能设为【%S】",InfoConstant.nothing_state,InfoConstant.executing_state));
//        }
        info.put(InfoConstant.state,InfoConstant.executing_state);
        return true;
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * 成功
     */
    public boolean success(){
//        String state = (String)info.getOrDefault(InfoConstant.state, InfoConstant.nothing_state);
//        if(!state.equals(InfoConstant.nothing_state)){
//            throw new RuntimeException(String.format("只有状态为【%S】时，才能设为【%S】",InfoConstant.nothing_state,InfoConstant.success_state));
//        }
        info.put(InfoConstant.state,InfoConstant.success_state);
        return true;
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
     * 删除
     *
     * @param key
     * @return {@link String }
     */
    public Object remove(String key){
        return info.remove(key);
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * 包含
     *
     * @param key
     * @return {@link String }
     */
    public boolean containsKey(String key){
        return info.containsKey(key);
    }



    /**
     * -----------------------------------------------------------------------------------------------------------------
     * JSON格式的信息
     *
     * @return {@link String }
     */
    public  String toJson(){
        if(info.containsKey(InfoConstant.startTime) && info.get(InfoConstant.startTime) instanceof Long){
            info.put(InfoConstant.startTime, LocalDateTime.ofInstant(
                            Instant.ofEpochMilli((long) info.get(InfoConstant.startTime)), ZoneId.systemDefault())
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
    public  String toJsonPretty(){
        if(info.containsKey(InfoConstant.startTime) && info.get(InfoConstant.startTime) instanceof Long){
            info.put(InfoConstant.startTime, LocalDateTime.ofInstant(
                            Instant.ofEpochMilli((long) info.get(InfoConstant.startTime)), ZoneId.systemDefault())
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
        Object object = info.get(InfoConstant.desc);
        if(null == object || !(object instanceof String)) return null;
        return (String) object ;
    }

    public String getStart(){
        Object object = info.get(InfoConstant.startTime);
        if(null == object || !(object instanceof String)) return null;
        return (String) object ;
    }
    public LocalDateTime getStartDateTime(){
        Object object = info.get(InfoConstant.startTime);
        if(null == object || !(object instanceof String)) return null;
        return LocalDateTime.parse((String) object, formatter) ;
    }
    public Long getStartTimestamp(){
        Object object = info.get(InfoConstant.startTime);
        if(null == object || !(object instanceof String)) return null;
        return LocalDateTime.parse((String) object, formatter).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli() ;
    }

    public Long getRunningTime(){
        Object object = info.get(InfoConstant.running);
        if(null == object || !(object instanceof Long)) return null;
        return (Long) object ;
    }

    public Integer getCount(String key){
        Object object = info.get(key);
        if(null == object || !(object instanceof AtomicInteger)) return null;
        return ((AtomicInteger)object).get() ;
    }

    public String getStr(String key){
        Object object = info.get(key);
        if(null == object || !(object instanceof String)) return null;
        return (String) object ;
    }

    public String getID(){
        Object object = info.get(InfoConstant.id);
        if(null == object || !(object instanceof String)) return null;
        return (String) object ;
    }

    public String getState(){
        Object object = info.get(InfoConstant.state);
        if(null == object || !(object instanceof String)) return null;
        return (String) object ;
    }
    
    public boolean isSuccess(){
        return InfoConstant.success_state.equals(getState());
    }

    public boolean isNothing(){
        return InfoConstant.nothing_state.equals(getState());
    }

    public boolean isFailed(){
        return InfoConstant.failed_state.equals(getState());
    }

    public boolean isExceptional(){
        return InfoConstant.exceptional_state.equals(getState());
    }
    public boolean isExecuting(){
        return InfoConstant.executing_state.equals(getState());
    }





    public Map<String, Object> getInfo() {
        return info;
    }

    public void setInfo(Map<String, Object> info) {
        this.info = info;
    }


}
