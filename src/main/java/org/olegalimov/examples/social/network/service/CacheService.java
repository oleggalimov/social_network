package org.olegalimov.examples.social.network.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.RedissonMapCache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

@Service
@Slf4j
@RequiredArgsConstructor
public class CacheService<T> {
    private final CacheManager cacheManager;

    public Collection<T> getAllFromCache(String cacheName) {
        var cache = cacheManager.getCache(cacheName);
        if (cache == null) {
            log.warn("Кеш {} не найден!", cacheName);
            return List.of();
        }
        return ((RedissonMapCache<String, T>) cache.getNativeCache()).values();
    }

    public void putToCache(String cacheName, List<T> objectList, Function<T, String> mapingFunction) {
        var cache = cacheManager.getCache(cacheName);
        if (cache == null) {
            log.error("Кеш {} не найден!", cacheName);
            return;
        }
        for (T element : objectList) {
            cache.putIfAbsent(mapingFunction.apply(element), element);
        }
    }

    public void invalidateCache(String cacheName) {
        var cache = cacheManager.getCache(cacheName);
        if (cache == null) {
            log.warn("Кеш {} не найден!", cacheName);
            return;
        }
        cache.invalidate();
    }
}
