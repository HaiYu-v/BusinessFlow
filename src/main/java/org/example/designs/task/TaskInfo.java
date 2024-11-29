package org.example.designs.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 任务信息
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
    public String desc;
    public Integer id;
    public TaskStatusEnum state;
    public Integer sort;
    public LocalDateTime startTime;
    public LocalDateTime endTime;
    public Long runningTime;
    public AtomicInteger countExecute;

    public TaskInfo() {
    }
    public TaskInfo(String desc, Integer id, TaskStatusEnum state, Integer sort, LocalDateTime startTime, LocalDateTime endTime, Long runningTime, AtomicInteger countExecute) {
        this.desc = desc;
        this.id = id;
        this.state = state;
        this.sort = sort;
        this.startTime = startTime;
        this.endTime = endTime;
        this.runningTime = runningTime;
        this.countExecute = countExecute;
    }

    public TaskInfo(TaskInfo info) {
        this.desc = info.desc;
        this.id = info.id;
        this.state = info.state;
        this.sort = info.sort;
        this.startTime = info.startTime;
        this.endTime = info.endTime;
        this.runningTime = info.runningTime;
        this.countExecute = info.countExecute;
    }

    // 获取运行时间的方法
    public Long getRunningTime() {
        if(startTime == null || endTime == null){
            return -1L;
        }
        return ChronoUnit.MILLIS.between(startTime, endTime);
    }

    // 转换为 JSON 字符串的方法
    public String toJSON() {
        StringBuilder info = new StringBuilder("{");
        info.append("\"desc\":\"").append(desc == null ? "null" : desc).append("\",");
        info.append("\"id\":\"").append(id == null ? "null" : id).append("\",");
        info.append("\"state\":\"").append(state == null ? "null" : state.getMessage()).append("\",");
        info.append("\"startTime\":\"").append(startTime == null ? "null" : startTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).append("\",");
        info.append("\"endTime\":\"").append(endTime == null ? "null" : endTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).append("\",");
        this.runningTime = getRunningTime();
        info.append("\"runningTime\":\"").append(runningTime).append("\",");
        info.append("\"countExecute\":\"").append(countExecute == null ? "null" : countExecute.get()).append("\"}");
        info.append("\"sort\":\"").append(sort == null ? "null" : sort).append("\",");
        return info.toString();
    }



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public TaskStatusEnum getState() {
        return state;
    }

    public void setState(TaskStatusEnum state) {
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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
