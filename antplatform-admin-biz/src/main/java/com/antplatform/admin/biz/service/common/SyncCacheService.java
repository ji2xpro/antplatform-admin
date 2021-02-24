package com.antplatform.admin.biz.service.common;

/**
 * @author: maoyan
 * @date: 2021/2/8 14:41:55
 * @description:
 */
public interface SyncCacheService {
    /**
     * 获取redis中key的锁，乐观锁实现
     * @param lockName
     * @param expireTime 锁的失效时间
     * @return
     */
    Boolean getLock(String lockName, int expireTime);

    /**
     * 释放锁，直接删除key(直接删除会导致任务重复执行，所以释放锁机制设为超时30s)
     * @param lockName
     * @return
     */
    Boolean releaseLock(String lockName);
}
