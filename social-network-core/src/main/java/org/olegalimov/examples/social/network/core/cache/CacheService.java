package org.olegalimov.examples.social.network.core.cache;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

@Slf4j
public abstract class CacheService<T> {

    @Autowired(required = false)
    private CacheManager cacheManager;

    public abstract Collection<T> mapFromCache(Cache cache);

    public Collection<T> getAllFromCache(String cacheName) {
        Cache cache = getCache(cacheName);
        if (cache == null) {
            log.warn("Кеш {} не найден!", cacheName);
            return List.of();
        }
        return mapFromCache(cache);
    }

    public T getFromCache(String cacheName, Object key, Class<T> targetType) {
        var cache = getCache(cacheName);
        if (cache == null) {
            return null;
        }
        return cache.get(key, targetType);
    }

    public void putToCache(String cacheName, List<T> objectList, Function<T, String> mapingFunction) {
        Cache cache = getCache(cacheName);
        if (cache == null) {
            log.error("Кеш {} не найден!", cacheName);
            return;
        }
        for (T element : objectList) {
            cache.putIfAbsent(mapingFunction.apply(element), element);
        }
    }

    public void invalidateCache(String cacheName) {
        Cache cache = getCache(cacheName);
        if (cache == null) {
            log.warn("Кеш {} не найден!", cacheName);
            return;
        }
        cache.invalidate();
    }

    private Cache getCache(String cacheName) {
        return cacheManager == null ? null : cacheManager.getCache(cacheName);
    }
}
