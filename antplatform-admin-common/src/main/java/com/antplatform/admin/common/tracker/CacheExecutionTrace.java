package com.antplatform.admin.common.tracker;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Cache Execution Trace
 *
 * @author: maoyan
 * @date: 2020/9/1 16:19:34
 * @description:
 */
public class CacheExecutionTrace implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 8419811231396904907L;

    /**
     * cache consumed time, in nanoseconds
     */
    private long timeConsumed;

    private Map<String, Integer> relatedKeys = new HashMap<String, Integer>();

    public long getTimeConsumed() {
        return timeConsumed;
    }

    public void setTimeConsumed(long timeConsumed) {
        this.timeConsumed = timeConsumed;
    }

    public void addTimeConsumed(long timeConsumed) {
        this.timeConsumed += timeConsumed;
    }

    public Map<String, Integer> getRelatedKeys() {
        return relatedKeys;
    }

    public void setRelatedKeys(Map<String, Integer> details) {
        this.relatedKeys = details;
    }

    public void addRelatedKey(String cacheKey) {
        if (!relatedKeys.containsKey(cacheKey)) {
            relatedKeys.put(cacheKey, new Integer(1));
        } else {
            relatedKeys.put(cacheKey, relatedKeys.get(cacheKey) + 1);
        }
    }

}
