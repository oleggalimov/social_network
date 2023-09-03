package org.oleggalimov.examples.social.network.counter.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.olegalimov.examples.social.network.core.cache.CacheService;
import org.olegalimov.examples.social.network.core.dto.CounterDto;
import org.springframework.cache.Cache;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@Slf4j
@RequiredArgsConstructor
public class CounterCacheService extends CacheService<CounterDto> {
    @Override
    public Collection<CounterDto> mapFromCache(Cache cache) {
        if (cache == null) {
            return null;
        }
        if (cache instanceof CaffeineCache caffeineCache) {
            return caffeineCache.getNativeCache().asMap().values().stream()
                    .map(obj -> (CounterDto) obj)
                    .toList();
        }
        return null;
    }
}
