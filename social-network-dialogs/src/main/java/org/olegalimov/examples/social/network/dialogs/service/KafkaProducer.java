package org.olegalimov.examples.social.network.dialogs.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class KafkaProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String topic, String key, String data ) {
        log.info("Отправляем сообщение {} в топик {} с ключом {}", data, topic, key);
        kafkaTemplate.send(topic, key, data);
    }
}
