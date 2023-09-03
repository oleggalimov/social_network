package org.olegalimov.examples.social.network.dialogs.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.olegalimov.examples.social.network.core.dto.CounterDto;
import org.springframework.stereotype.Service;

import static org.olegalimov.examples.social.network.core.constant.KafkaConstants.UNREAD_MESSAGES_COUNTER_TOPIC;

@Service
@AllArgsConstructor
@Slf4j
public class MessageCountService {

    private final KafkaProducer kafkaProducer;
    private final ObjectMapper objectMapper;

    public void incrementUnreadMessagesCounter(String userId) {
        sendCounterUpdate(userId, 1);
    }

    public void decreaseUnreadMessagesCounter(String userId, int count) {
        sendCounterUpdate(userId, -count);
    }

    public void sendCounterUpdate(String userId, int count) {
        log.info("Отправляем обновление сообщений для пользователя {}: {}", userId, count);
        kafkaProducer.sendMessage(UNREAD_MESSAGES_COUNTER_TOPIC, null, toCounterDtoString(userId, count));
    }

    @SneakyThrows
    private String toCounterDtoString(String userId, int count) {
        return objectMapper.writeValueAsString(new CounterDto()
                .setUserId(userId)
                .setCount(count));
    }
}
