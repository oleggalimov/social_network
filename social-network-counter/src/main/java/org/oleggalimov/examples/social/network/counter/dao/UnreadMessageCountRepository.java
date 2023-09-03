package org.oleggalimov.examples.social.network.counter.dao;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

import static org.olegalimov.examples.social.network.core.constant.Queries.Insert.UPSERT_UNREAD_MESSAGES_COUNT;
import static org.olegalimov.examples.social.network.core.constant.Queries.Select.SELECT_UNREAD_MSG_COUNT;

@Service
@Slf4j
@AllArgsConstructor
public class UnreadMessageCountRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public int updateUnreadMsgCounter(String userId, Integer diff) {
        var currentVal = getUnreadMsgCounter(userId);

        var newCount = diff + currentVal;
        if (newCount < 0) {
            log.info("Для пользователя {} нельзя установить отрицательное значение " +
                    "непрочитанных сообщений: {}, сбрасываем в 0",
                    userId,
                    newCount);
            newCount = 0;
        }
        jdbcTemplate.update(UPSERT_UNREAD_MESSAGES_COUNT, Map.of("userId", userId, "newCount", newCount));

        return newCount;

    }

    public int getUnreadMsgCounter(String userId) {
        return Optional.of(jdbcTemplate.queryForList(
                        SELECT_UNREAD_MSG_COUNT,
                        Map.of("userId", userId),
                        Integer.class))
                .map(response -> response.isEmpty() ? 0 : response.get(0))
                .orElse(0);
    }
}
