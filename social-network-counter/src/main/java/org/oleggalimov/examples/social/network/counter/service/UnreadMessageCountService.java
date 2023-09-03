package org.oleggalimov.examples.social.network.counter.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.olegalimov.examples.social.network.core.dto.CounterDto;
import org.oleggalimov.examples.social.network.counter.dao.UnreadMessageCountRepository;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

import static org.oleggalimov.examples.social.network.counter.constant.CommonConstants.UNREAD_MSG_COUNTER_CACHE;

@Service
@Slf4j
@AllArgsConstructor
public class UnreadMessageCountService {

    private final UnreadMessageCountRepository messageCountRepository;
    private final CounterCacheService cacheService;

    @CachePut(cacheNames = UNREAD_MSG_COUNTER_CACHE, key = "#dto.userId")
    public CounterDto updateUnreadMsgCounter(CounterDto dto) {
        var newCount = messageCountRepository.updateUnreadMsgCounter(dto.getUserId(), dto.getCount());
        var result = new CounterDto()
                .setUserId(dto.getUserId())
                .setCount(newCount);
        log.info("Измененное количество: {}", result);
        return result;
    }

    @CachePut(cacheNames = UNREAD_MSG_COUNTER_CACHE, key = "#userId")
    public CounterDto getUnreadMsgCounter(String userId) {
        var counterDto = cacheService.getFromCache(UNREAD_MSG_COUNTER_CACHE, userId, CounterDto.class);
        if (counterDto == null) {
            log.warn("Для пользователя {} в кеше не найден счетчик, обращаемся к БД", userId);
            return new CounterDto()
                    .setCount(getAllCounterFromDatabase(userId))
                    .setUserId(userId);
        }
        return counterDto;
    }

    public int getAllCounterFromDatabase(String userId) {
        log.info("Для пользователя {} запрашиваем данные из БД", userId);
        return messageCountRepository.getUnreadMsgCounter(userId);
    }
}
