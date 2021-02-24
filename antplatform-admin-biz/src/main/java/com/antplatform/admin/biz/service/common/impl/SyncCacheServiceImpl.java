package com.antplatform.admin.biz.service.common.impl;

import com.antplatform.admin.biz.infrastructure.shiro.jwt.JwtProperties;
import com.antplatform.admin.biz.service.common.SyncCacheService;
import com.antplatform.admin.common.base.Constant;
import com.antplatform.admin.common.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.stereotype.Service;

/**
 * @author: maoyan
 * @date: 2021/2/8 14:40:16
 * @description:
 */
@Slf4j
@Service("SyncCacheService")
public class SyncCacheServiceImpl implements SyncCacheService {

    @Autowired
    JwtProperties jwtProperties;
//    @Autowired
//    JedisUtils jedisUtils;

    /**
     * 获取redis中key的锁，乐观锁实现
     *
     * @param lockName
     * @param expireTime 锁的失效时间
     * @return
     */
    @Override
    public Boolean getLock(String lockName, int expireTime) {
        Boolean result = Boolean.FALSE;
        try {
            boolean isExist = RedisUtil.hasKey(lockName);
            if (!isExist) {
                RedisUtil.incr(lockName, 0);
                RedisUtil.expire(lockName, expireTime <= 0 ? Constant.ExpireTime.ONE_HOUR : expireTime);
            }
            long reVal = RedisUtil.incr(lockName, 1);
            if (1L == reVal) {
                //获取锁
                result = Boolean.TRUE;
                log.info("获取redis锁:" + lockName + ",成功");
            } else {
                log.info("获取redis锁:" + lockName + ",失败" + reVal);
            }
        } catch (Exception e) {
            log.error("获取redis锁失败:" + lockName, e);
        }
        return result;
    }

    /**
     * 释放锁，直接删除key(直接删除会导致任务重复执行，所以释放锁机制设为超时30s)
     *
     * @param lockName
     * @return
     */
    @Override
    public Boolean releaseLock(String lockName) {
        Boolean result = Boolean.FALSE;
        try {
            RedisUtil.expire(lockName, Constant.ExpireTime.TEN_SEC);
            log.info("释放redis锁:" + lockName + ",成功");
        } catch (Exception e) {
            log.error("释放redis锁失败:" + lockName, e);
        }
        return result;
    }
}
