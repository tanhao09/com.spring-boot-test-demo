package com.springboottest.service.impl;

import com.fasterxml.jackson.databind.JavaType;
import com.springboottest.config.RedisSettings;
import com.springboottest.service.redis.RedisService;
import com.springboottest.utils.JsonUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import redis.clients.jedis.*;
import redis.clients.util.Pool;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Supplier;

/**
 * Created by ymind on 2018/4/18.
 */
@Service
public class RedisServiceImpl implements RedisService {
    private static Logger logger = Logger.getLogger(RedisServiceImpl.class);
    private Pool pool = null;

    @Autowired
    private RedisSettings redisSettings;

    @Value("${redis.cache.prefix}")
    private String cachePrefix;

    @PostConstruct
    public void init() {
        logger.debug("init hosts:" + redisSettings.serverHosts);

        if (pool != null) return;

        List<JedisShardInfo> redisHosts = getRedisHosts();

        if (redisHosts.size() != 1) return;
        if (redisHosts.size() <= 0) return;

        if (redisHosts.size() == 1) {
            JedisShardInfo server = redisHosts.get(0);
            String hostURI = "redis://:" + server.getPassword() + "@" + server.getHost() + ":" + server.getPort();

            logger.debug("init Pool<JedisPool>:" + hostURI);

            pool = new JedisPool(getPoolConfig(), server.getHost(), server.getPort(), redisSettings.timeout, server.getPassword());
        } else pool = new ShardedJedisPool(getPoolConfig(), redisHosts);
    }

    public List<JedisShardInfo> getRedisHosts() {
        List<JedisShardInfo> hosts = new ArrayList<JedisShardInfo>();
        String[] servers = redisSettings.serverHosts.split(";");

        for (String server : servers) {
            //@：分隔密码以及host
            String host = server.trim();

            if (host.equals("")) continue;

            JedisShardInfo info = new JedisShardInfo(host);

            if (info.getPassword() != null && info.getPassword().trim().equals("")) info.setPassword(null);

            hosts.add(info);
        }

        return hosts;
    }

    public JedisPoolConfig getPoolConfig() {
        JedisPoolConfig redisConf = new JedisPoolConfig();

        logger.debug("redis config: maxIdle:" + redisSettings.maxIdle + ",maxWait:" + redisSettings.maxWait + ",maxTotal:" + redisSettings.maxTotal);

        redisConf.setMaxIdle(redisSettings.maxIdle);
        redisConf.setMaxWaitMillis(redisSettings.maxWait);
        redisConf.setMaxTotal(redisSettings.maxTotal);
        redisConf.setTestOnBorrow(false);
        redisConf.setTestOnReturn(true);

        return redisConf;
    }

    public String checkKey(String key) {
        return key.startsWith(cachePrefix + "_") ? key : cachePrefix + "_" + key;
    }

    @Override
    public boolean setValue(String key, int value) {
        return setValue(key, String.valueOf(value));
    }


    @Override
    public boolean setValue(String key, int value, int seconds) {
        return setValue(key, String.valueOf(value), seconds);
    }

    @Override
    public boolean setValue(String key, String value) {
        return setValue(key, value, 0);
    }

    @Override
    public boolean setValue(String key, String value, int seconds) {
        if (pool == null) return false;

        String redisKey = checkKey(key);
        JedisCommands jedis = null;

        try {
            jedis = (JedisCommands) pool.getResource();

            if (seconds > 0) {
                jedis.setex(redisKey, seconds, value);
            } else {
                jedis.set(redisKey, value);
            }
        } catch (Exception e) {
            e.printStackTrace();

            if (jedis != null) pool.returnBrokenResource(jedis);

            return false;
        } finally {
            if (jedis != null) pool.returnResource(jedis);
        }

        return true;
    }

    @Override
    public boolean setValue(String key, Date d) {
        return this.setValue(key, String.valueOf(d.getTime()));
    }

    @Override
    public boolean setJsonObject(String key, Object data, int seconds) {
        String json = JsonUtil.toJson(data);

        return this.setValue(key, json, seconds);
    }

    @Override
    public String getValue(String key) {
        JedisCommands jedis = null;
        String value = null;
        String redisKey = checkKey(key);

        try {
            jedis = (JedisCommands) pool.getResource();

            value = jedis.get(redisKey);
        } catch (Exception e) {
            e.printStackTrace();

            if (jedis != null) pool.returnBrokenResource(jedis);

            return null;
        } finally {
            try{
                if (jedis != null)
                    pool.returnResource(jedis);
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        return value;
    }

    @Override
    public int getInt(String key) {
        int value = 0;
        String s = getValue(key);

        if (s != null) {
            try {
                value = Integer.parseInt(s);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return value;
    }

    @Override
    public Date getDate(String key) {
        String s = getValue(key);
        long time = Long.valueOf(s==null?"0":s);

        return new Date(time);
    }

    @Override
    public <T> T getJsonObject(String key, Class<T> valueType) {
        String json = this.getValue(key);

        return JsonUtil.fromJson(json, valueType);
    }


    @Override
    public <T> T getJsonObject(String key, JavaType javaType) {
        String json = this.getValue(key);

        return JsonUtil.fromJson(json, javaType);
    }

    @Override
    public <T> T getJsonObject(String key, Class<T> valueType, int seconds, Supplier<T> createFunction) {
        T item = this.getJsonObject(key, valueType);

        if (item == null) {
            item = createFunction.get();

            this.setJsonObject(key, item, seconds);
        }

        return item;
    }

    @Override
    public <T> T getJsonObject(String key, JavaType javaType, int seconds, Supplier<T> createFunction) {
        T item = this.getJsonObject(key, javaType);

        if (item == null) {
            item = createFunction.get();

            this.setJsonObject(key, item, seconds);
        }

        return item;
    }

    @Override
    public String lpop(String key) {
        JedisCommands jedis = null;
        String value = null;
        String redisKey = checkKey(key);

        try {
            jedis = (JedisCommands) pool.getResource();

            value = jedis.lpop(redisKey);
        } catch (Exception e) {
            e.printStackTrace();

            if (jedis != null) pool.returnBrokenResource(jedis);

            return null;
        } finally {
            if (jedis != null) pool.returnResource(jedis);
        }

        return value;
    }

    @Override
    public String rpop(String key) {
        JedisCommands jedis = null;
        String value = null;
        String redisKey = checkKey(key);

        try {
            jedis = (JedisCommands) pool.getResource();

            value = jedis.rpop(redisKey);
        } catch (Exception e) {
            e.printStackTrace();

            if (jedis != null) pool.returnBrokenResource(jedis);

            return null;
        } finally {
            if (jedis != null) pool.returnResource(jedis);
        }

        return value;
    }

    @Override
    public List<String> getList(String key) {
        JedisCommands jedis = null;
        List<String> value = null;
        String redisKey = checkKey(key);

        try {
            jedis = (JedisCommands) pool.getResource();

            value = jedis.lrange(redisKey, 0, -1);
        } catch (Exception e) {
            e.printStackTrace();

            if (jedis != null) pool.returnBrokenResource(jedis);

            return null;
        } finally {
            if (jedis != null) pool.returnResource(jedis);
        }

        return value;
    }

    @Override
    public boolean lpush(String key, String value) {
        JedisCommands jedis = null;
        String redisKey = checkKey(key);

        try {
            jedis = (JedisCommands) pool.getResource();

            jedis.lpush(redisKey, value);
        } catch (Exception e) {
            e.printStackTrace();

            if (jedis != null) pool.returnBrokenResource(jedis);

            return false;
        } finally {
            if (jedis != null) pool.returnResource(jedis);
        }

        return true;
    }

    @Override
    public boolean rpush(String key, String value) {
        JedisCommands jedis = null;
        String redisKey = checkKey(key);

        try {
            jedis = (JedisCommands) pool.getResource();

            jedis.rpush(redisKey, value);
        } catch (Exception e) {
            e.printStackTrace();

            if (jedis != null) pool.returnBrokenResource(jedis);

            return false;
        } finally {
            if (jedis != null) pool.returnResource(jedis);
        }
        return true;
    }

    @Override
    public boolean del(String key) {
        JedisCommands jedis = null;
        String redisKey = checkKey(key);

        try {
            jedis = (JedisCommands) pool.getResource();

            jedis.del(redisKey);
        } catch (Exception e) {
            e.printStackTrace();
            if (jedis != null) pool.returnBrokenResource(jedis);
            return false;
        } finally {
            if (jedis != null) pool.returnResource(jedis);
        }

        return true;
    }

    @Override
    public Pool getPool() {
        return pool;
    }
}
