package com.antplatform.admin.common.tracker;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Sql Execution Trace
 *
 * @author: maoyan
 * @date: 2020/9/1 16:18:23
 * @description:
 */
public class SqlExecutionTrace implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 889973049111731032L;

    /**
     * sql executed times
     */
    private int times;

    /**
     * sql consumed time, in nanoseconds
     */
    private long timeConsumed;

    private List<SqlDetail> details = new ArrayList<SqlDetail>();

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }

    public void incrTime() {
        this.times++;
    }

    public long getTimeConsumed() {
        return timeConsumed;
    }

    public void setTimeConsumed(long timeConsume) {
        this.timeConsumed = timeConsume;
    }

    public void addTimeConsumed(long timeConsumed) {
        this.timeConsumed += timeConsumed;
    }

    public List<SqlDetail> getDetails() {
        return details;
    }

    public void setDetails(List<SqlDetail> details) {
        this.details = details;
    }

    public void addDetail(SqlDetail detail) {
        this.details.add(detail);
    }

    public void addDetail(long executedTime, String executedOn, long timeConsumed, String content) {
        addDetail(new SqlDetail(executedTime, executedOn, timeConsumed, content));
    }

}