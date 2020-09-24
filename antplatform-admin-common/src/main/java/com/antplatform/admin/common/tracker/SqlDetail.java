package com.antplatform.admin.common.tracker;

import java.io.Serializable;

/**
 * Sql Detail
 *
 * @author: maoyan
 * @date: 2020/9/1 16:18:57
 * @description:
 */
public class SqlDetail implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -6695793641559962377L;

    /**
     * in nanoseconds
     */
    private long executedTime;

    /**
     * which database the sql is executed on
     */
    private String executedOn;

    /**
     * in nanoseconds
     */
    private long timeConsumed;

    private String content;

    public SqlDetail() {
    }

    public SqlDetail(long executeTime, String executeOn, long timeConsumed, String content) {
        this.executedTime = executeTime;
        this.executedOn = executeOn;
        this.timeConsumed = timeConsumed;
        this.content = content;
    }

    public long getExecutedTime() {
        return executedTime;
    }

    public void setExecutedTime(long executeTime) {
        this.executedTime = executeTime;
    }

    public String getExecutedOn() {
        return executedOn;
    }

    public void setExecutedOn(String executeOn) {
        this.executedOn = executeOn;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getTimeConsumed() {
        return timeConsumed;
    }

    public void setTimeConsumed(long timeConsumed) {
        this.timeConsumed = timeConsumed;
    }

}