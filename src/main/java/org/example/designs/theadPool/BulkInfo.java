package org.example.designs.theadPool;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * @Program: 11.designs
 * @Author: 海里的鱼
 * @Create: 2024-11-07 15:06
 * @Name: TODO
 * @Desc: TODO
 */
public class BulkInfo {
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int executeCount;
    private List<String> successTasks;
    private List<String> failTasks;


    // 转换为 JSON 字符串的方法
    public String toJsonString() {
        StringBuilder json = new StringBuilder("{");

        json.append("\"success\":").append(successTasks.size()).append(",");
        json.append("\"fail\":").append(failTasks.size()).append(",");
        json.append("\"count\":").append(successTasks.size() + failTasks.size()).append(",");
        json.append("\"startTime\":\"").append(startTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).append("\",");
        json.append("\"endTime\":\"").append(endTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).append("\",");
        json.append("\"runningTime\":").append(ChronoUnit.MILLIS.between(startTime, endTime)).append(",");
        json.append("\"ExecuteCount\":").append(executeCount);

        json.append("}");
        return json.toString();
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

    public int getExecuteCount() {
        return executeCount;
    }

    public void setExecuteCount(int executeCount) {
        this.executeCount = executeCount;
    }

    public List<String> getSuccessTasks() {
        return successTasks;
    }

    public void setSuccessTasks(List<String> successTasks) {
        this.successTasks = successTasks;
    }

    public List<String> getFailTasks() {
        return failTasks;
    }

    public void setFailTasks(List<String> failTasks) {
        this.failTasks = failTasks;
    }
}
