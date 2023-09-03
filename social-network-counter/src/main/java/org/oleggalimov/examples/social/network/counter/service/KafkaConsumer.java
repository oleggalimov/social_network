package org.oleggalimov.examples.social.network.counter.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.olegalimov.examples.social.network.core.dto.CounterDto;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import javax.validation.Validator;

import static org.olegalimov.examples.social.network.core.constant.KafkaConstants.UNREAD_MESSAGES_COUNTER_TOPIC;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaConsumer {
    private static final String GROUP_ID = "counter.changes.group";

    private final ObjectMapper objectMapper;
    private final Validator validator;
    private final UnreadMessageCountService unreadMessageCountService;


    @KafkaListener(topics = UNREAD_MESSAGES_COUNTER_TOPIC, groupId = GROUP_ID)
    public void onMessage(ConsumerRecord<String, String> message) {
        log.info("Сообщение из топика: {}", message.value());
        if (message.value() == null) {
            log.info("В сообщении нет тела");
            return;
        }
        CounterDto dto;
        try {
            dto = objectMapper.readValue(message.value(), CounterDto.class);
        } catch (JsonProcessingException e) {
            log.error("Не удалось прочитать сообщение", e);
            return;
        }
        if (isInvalidDto(message.value(), dto)) {
            return;
        }
        log.info("Смапленное сообщение: {}", dto);
        processMessage(message.value(), dto);
    }

    private boolean isInvalidDto(String message, CounterDto dto) {
        var constraintViolations = validator.validate(dto);
        if (constraintViolations == null || constraintViolations.isEmpty()) {
            return false;
        }
        log.error("Ошибки валидации - пропускаем дто. " +
                        "Исходное сообщение: {}. " +
                        "После мапинга: {}. " +
                        "Ошибки валидации: {}",
                message, dto, constraintViolations);
        return true;
    }

    private void processMessage(String message, CounterDto dto) {
        try {
            unreadMessageCountService.updateUnreadMsgCounter(dto);
        } catch (Exception exception) {
            log.error("Не удалось обработать сообщение: {}", message, exception);
        }
    }
}
