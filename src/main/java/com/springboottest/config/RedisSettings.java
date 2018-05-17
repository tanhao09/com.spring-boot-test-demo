package com.springboottest.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by ymind on 2018/4/18.
 */

@Component
public class RedisSettings {
    @Value("${redis.server.hosts}")
    public String serverHosts;

    @Value("${redis.server.maxTotal}")
    public int maxTotal;

    @Value("${redis.server.maxIdle}")
    public int maxIdle;

    @Value("${redis.server.maxWait}")
    public int maxWait;

    @Value("${redis.server.timeout}")
    public int timeout;
}
