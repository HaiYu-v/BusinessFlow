package org.example.designs.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * TODO
 *
 * <p>
 *     TODO
 * </p>
 *
 * @author HaiYu
 * @version 1.0.0
 * @date 2024-11-19
 */
public class TaskInfo {
    private Integer id;
    private String name;
    private TaskEnum state;
    private Integer sort;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private AtomicInteger countExecute;
    private String describe;

    // 获取运行时间的方法
    private Long getRunningTime() {
        if(startTime == null || endTime == null){
            return -1L;
        }
        return ChronoUnit.MILLIS.between(startTime, endTime);
    }

    // 转换为 JSON 字符串的方法
    public String toJsonString() {
        StringBuilder info = new StringBuilder("{");
        info.append("\"id\":\"").append(id == null ? "null" : id).append("\",");
        info.append("\"name\":\"").append(name == null ? "null" : name).append("\",");
        info.append("\"state\":\"").append(state == null ? "null" : state.getMessage()).append("\",");
        info.append("\"describe\":\"").append(describe == null ? "null" : describe).append("\",");
        info.append("\"sort\":\"").append(sort == null ? "null" : sort).append("\",");
        info.append("\"startTime\":\"").append(startTime == null ? "null" : startTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).append("\",");
        info.append("\"endTime\":\"").append(endTime == null ? "null" : endTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).append("\",");
        info.append("\"runningTime\":\"").append(getRunningTime()).append("\",");
        info.append("\"countExecute\":\"").append(countExecute == null ? "null" : countExecute.get()).append("\"}");
        return info.toString();
    }



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TaskEnum getState() {
        return state;
    }

    public void setState(TaskEnum state) {
        this.state = state;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public AtomicInteger getCountExecute() {
        return countExecute;
    }

    public void setCountExecute(AtomicInteger countExecute) {
        this.countExecute = countExecute;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }
}
