package com.antplatform.admin.common.tracker;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Execution Context
 *
 * @author: maoyan
 * @date: 2020/9/1 16:16:53
 * @description:
 */
public class ExecutionContext implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -6835922953338060000L;

    private TrackerContext trackerContext;

    private final Map<String, Object> contextData = new HashMap<String, Object>();

    private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    public TrackerContext getTrackerContext() {
        return trackerContext;
    }

    public void setTrackerContext(TrackerContext trackerContext) {
        this.trackerContext = trackerContext;
    }

    public ExecutionContext add(String key, Object value) {
        readWriteLock.writeLock().lock();
        try {
            contextData.put(key, value);
            return this;
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

    public ExecutionContext addIfNotExists(String key, Object value) {
        readWriteLock.writeLock().lock();
        try {
            if (!contextData.containsKey(key)) {
                contextData.put(key, value);
            }
            return this;
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

    public ExecutionContext clear(String key) {
        readWriteLock.writeLock().lock();
        try {
            contextData.remove(key);
            return this;
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

    public ExecutionContext clear() {
        readWriteLock.writeLock().lock();
        try {
            contextData.clear();
            return this;
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

    @SuppressWarnings("unchecked")
    public <T> T get(String key) {
        readWriteLock.readLock().lock();
        try {
            return (T) contextData.get(key);
        } finally {
            readWriteLock.readLock().unlock();
        }
    }

    @SuppressWarnings("unchecked")
    public <T> T get(String key, T defaultIfNull) {
        readWriteLock.readLock().lock();
        try {
            T data = (T) get(key);
            return data != null ? data : defaultIfNull;
        } finally {
            readWriteLock.readLock().unlock();
        }
    }

}
