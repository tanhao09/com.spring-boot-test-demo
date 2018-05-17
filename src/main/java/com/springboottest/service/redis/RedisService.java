package com.springboottest.service.redis;

import com.fasterxml.jackson.databind.JavaType;
import redis.clients.util.Pool;

import java.util.Date;
import java.util.List;
import java.util.function.Supplier;

/**
 * Created by ymind on 2018/4/18.
 */
public interface RedisService {
    boolean setValue(String key, String value);
    boolean setValue(String key, int value);
    boolean setValue(String key, int value, int seconds);
    boolean setValue(String key, String value, int seconds);
    boolean setValue(String key, Date d);
    boolean setJsonObject(String key, Object data, int seconds);
    String getValue(String key);
    int getInt(String key);
    Date getDate(String key);
    <T> T getJsonObject(String key, Class<T> valueType);
    <T> T getJsonObject(String key, JavaType javaType);
    <T> T getJsonObject(String key, Class<T> valueType, int seconds, Supplier<T> createFunction);
    <T> T getJsonObject(String key, JavaType javaType, int seconds, Supplier<T> createFunction);
    String lpop(String key);
    String rpop(String key);
    boolean lpush(String key, String value);
    boolean rpush(String key, String value);
    List<String> getList(String key);
    boolean del(String key);
    Pool getPool();
}
