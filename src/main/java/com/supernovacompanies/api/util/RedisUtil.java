package com.supernovacompanies.api.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

/**
 * @author: nolan
 * @date: 8/27/21
 */
@Component
public class RedisUtil {
    @Autowired
    private RedisTemplate<String, String> ssRedisTemplate;
    private ValueOperations<String, String> ssValueOpr;

    public RedisUtil() {
    }

    @PostConstruct
    public void postConstruct() {
        this.ssValueOpr = this.ssRedisTemplate.opsForValue();
    }

    public void set(String key, String value) {
        this.ssValueOpr.set(key, value);
    }

    public void setEx(String key, String value, long timeoutSeconds) {
        this.ssValueOpr.set(key, value, timeoutSeconds, TimeUnit.SECONDS);
    }

    public String get(String key) {
        return (String)this.ssValueOpr.get(key);
    }

    public String getEx(String key, long timeoutSeconds) {
        this.ssRedisTemplate.expire(key, timeoutSeconds, TimeUnit.SECONDS);
        return (String)this.ssValueOpr.get(key);
    }

    public void delete(String key) {
        this.ssRedisTemplate.delete(key);
    }

    public long increment(String key) {
        return this.ssValueOpr.increment(key, 1L);
    }

    public long getExpireTime(String key) {
        return this.ssRedisTemplate.boundValueOps(key).getExpire();
    }
}
