package com.antplatform.admin.biz.infrastructure.shiro.cache;

import com.antplatform.admin.biz.infrastructure.shiro.jwt.JwtProperties;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: maoyan
 * @date: 2021/2/8 19:23:16
 * @description:
 */
@Service
public class ShiroCacheManager implements CacheManager {

//    @Autowired
//    JedisUtils jedisUtils;

    @Autowired
    JwtProperties jwtProperties;

    @Override
    public <K, V> Cache<K, V> getCache(String s) throws CacheException {
//        return new ShiroCache<K,V>(jedisUtils,jwtProperties);
        return new ShiroCache<K,V>(jwtProperties);
    }
}
