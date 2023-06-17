package org.olegalimov.examples.social.network.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.spring.cache.CacheConfig;
import org.redisson.spring.cache.RedissonSpringCacheManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.olegalimov.examples.social.network.constant.CommonConstant.POSTS_CACHE_NAME;

@Configuration
@EnableCaching
public class ApplicationCacheConfig {

    private final long DEFAULT_CACHE_ITEM_TTL = TimeUnit.MINUTES.toMillis(3);
    private final long DEFAULT_CACHE_MAX_IDLE_TIME = TimeUnit.MINUTES.toMillis(5);
    private final int DEFAULT_CACHE_MAX_SIZE = 1000;

    @Value("${spring.redis.host}")
    private String redisHost;

    @Value("${spring.redis.port}")
    private int redisPort;

    @Bean(destroyMethod = "shutdown")
    RedissonClient redisson() {
        var host = "redis://" + redisHost + ":" + redisPort;
        Config config = new Config();
        config
                .useSingleServer()
                .setAddress(host);
        return Redisson.create(config);
    }

    @Bean
    public CacheManager cacheManager(RedissonClient redissonClient) {
        Map<String, CacheConfig> config = new HashMap<>();
        CacheConfig cacheConfig = new CacheConfig(DEFAULT_CACHE_ITEM_TTL, DEFAULT_CACHE_MAX_IDLE_TIME);
        cacheConfig.setMaxSize(DEFAULT_CACHE_MAX_SIZE);
        config.put(POSTS_CACHE_NAME, cacheConfig);

        return new RedissonSpringCacheManager(redissonClient, config);
    }
}
