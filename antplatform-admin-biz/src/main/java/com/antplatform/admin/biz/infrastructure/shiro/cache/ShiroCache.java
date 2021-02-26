package com.antplatform.admin.biz.infrastructure.shiro.cache;

import com.antplatform.admin.biz.infrastructure.shiro.jwt.JwtProperties;
import com.antplatform.admin.biz.infrastructure.shiro.jwt.JwtUtil;
import com.antplatform.admin.biz.infrastructure.shiro.realm.JwtRealm;
import com.antplatform.admin.common.base.Constants.SecurityConstant;
import com.antplatform.admin.common.utils.RedisUtil;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * 重写Shiro的Cache保存读取
 *
 * @author: maoyan
 * @date: 2021/2/8 19:19:41
 * @description:
 */
public class ShiroCache<K,V> implements Cache<K,V> {

//    private JedisUtils jedisUtils;

    private JwtProperties jwtProperties;

//    public ShiroCache(JedisUtils jedisUtils,JwtProperties jwtProperties) {
//        this.jedisUtils = jedisUtils;
//        this.jwtProperties=jwtProperties;
//    }

    public ShiroCache(JwtProperties jwtProperties) {
        this.jwtProperties=jwtProperties;
    }

    /**
     * 获取缓存
     * @param key
     * @return
     * @throws CacheException
     */
    @Override
    public Object get(Object key) throws CacheException{
        String tempKey= this.getKey(key);
        Object result=null;
        if(RedisUtil.hasKey(tempKey)){
            result = RedisUtil.get(tempKey);
        }
        return result;
    }

    /**
     * 保存缓存
     * @param key
     * @param value
     * @return
     * @throws CacheException
     */
    @Override
    public Object put(Object key, Object value) throws CacheException {
        String tempKey= this.getKey(key);
        RedisUtil.set(tempKey, value,jwtProperties.getTokenExpireTime()*60);
        return value;
    }

    /**
     * 移除缓存
     * @param key
     * @return
     * @throws CacheException
     */
    @Override
    public Object remove(Object key) throws CacheException {
        String tempKey= this.getKey(key);
        if(RedisUtil.hasKey(tempKey)){
            RedisUtil.del(tempKey);
        }
        return null;
    }

    @Override
    public void clear() throws CacheException {

    }

    @Override
    public int size() {
        return 20;
    }

    @Override
    public Set<K> keys() {
        return null;
    }

    @Override
    public Collection<V> values() {
        Set keys = this.keys();
        List<V> values = new ArrayList<>();
        for (Object key : keys) {
            values.add((V)RedisUtil.get(this.getKey(key)));
        }
        return values;
    }

    /**
     * 缓存的key名称获取为shiro:cache:account
     * @param key {@link JwtRealm#doGetAuthenticationInfo(AuthenticationToken)} 指向的是SimpleAuthenticationInfo构造时第一个参数对象，目前是token
     * @return
     */
    private String getKey(Object key) {
        return SecurityConstant.PREFIX_SHIRO_CACHE + JwtUtil.getClaim(key.toString(), SecurityConstant.ACCOUNT);
    }

}
