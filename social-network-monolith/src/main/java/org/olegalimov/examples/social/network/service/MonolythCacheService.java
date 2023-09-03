package org.olegalimov.examples.social.network.service;

import lombok.extern.slf4j.Slf4j;
import org.olegalimov.examples.social.network.core.cache.CacheService;
import org.olegalimov.examples.social.network.dto.PostDto;
import org.redisson.RedissonMapCache;
import org.springframework.cache.Cache;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@Slf4j
public class MonolythCacheService extends CacheService<PostDto> {

    @Override
    public Collection<PostDto> mapFromCache(Cache cache) {
        return ((RedissonMapCache<String, PostDto>) cache.getNativeCache()).values();
    }
}
