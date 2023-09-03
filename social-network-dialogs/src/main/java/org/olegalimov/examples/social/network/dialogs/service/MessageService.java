package org.olegalimov.examples.social.network.dialogs.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.olegalimov.examples.social.network.core.entity.MessageEntity;
import org.olegalimov.examples.social.network.dialogs.dao.MessageRepository;
import org.olegalimov.examples.social.network.dialogs.grpc.Message;
import org.olegalimov.examples.social.network.dialogs.mapper.MessageMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageService {

    public final MessageRepository messageRepository;
    private final MessageMapper messageMapper;
    private final MessageCountService messageCountService;

    public boolean saveMessage(String fromUserId, String toUserId, String text) {
        if (!StringUtils.hasText(fromUserId)
                || !StringUtils.hasText(toUserId)
                || !StringUtils.hasText(text)
        ) {
            log.error("Один из аргментов пуст: fromUserId={}, toUserId={}, text={}", fromUserId, toUserId, text);
            throw new IllegalArgumentException("Один из аргументов пустой!");
        }

        var isSuccess = messageRepository.saveMessage(fromUserId, toUserId, text);
        if(isSuccess) {
            messageCountService.incrementUnreadMessagesCounter(toUserId);
        }
        return isSuccess;
    }

    public List<Message> getMessages(String fromUserId, String toUserId) {
        if (!StringUtils.hasText(fromUserId) || !StringUtils.hasText(toUserId)) {
            log.error("Один из пользователей не указан: fromUserId={}, toUserId={}", fromUserId, toUserId);
            throw new IllegalArgumentException("Один из пользователей не указан!");
        }

        var messages = messageRepository.findAllMessages(fromUserId, toUserId);

        log.info("По запросу диалога {} с {} найдено {} сообщений", fromUserId, toUserId, messages.size());
        decreaseUnreadMessage(fromUserId, messages);
        return messages.stream()
                .map(messageMapper::toProtobufMessage)
                .toList();
    }

    public List<Message> getMessages(String toUserId) {
        if (!StringUtils.hasText(toUserId)) {
            throw new IllegalArgumentException("Пользователь не указан!");
        }

        var messages = messageRepository.findAllMessages(toUserId);

        log.info("По запросу для {} найдено {} сообщений", toUserId, messages.size());
        var messagesList = messages.stream()
                .map(messageMapper::toProtobufMessage)
                .toList();
        decreaseUnreadMessage(toUserId, messages);
        return messagesList;
    }

    public void decreaseUnreadMessage(String userId, List<MessageEntity> messages) {
        var messagesCounter = messages.stream()
                .filter(messageEntity -> userId.equalsIgnoreCase(messageEntity.getToUserId()))
                .count();
        if(messagesCounter > 0) {
            messageCountService.decreaseUnreadMessagesCounter(userId, (int) messagesCounter);
        }
    }
}
