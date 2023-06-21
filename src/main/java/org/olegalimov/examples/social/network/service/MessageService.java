package org.olegalimov.examples.social.network.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.olegalimov.examples.social.network.dao.MessageRepository;
import org.olegalimov.examples.social.network.dto.MessageDto;
import org.olegalimov.examples.social.network.mapper.MessageMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageService {

    public final MessageRepository messageRepository;
    private final MessageMapper messageMapper;

    public boolean saveMessage(String fromUserId, String toUserId, String text) {
        if (!StringUtils.hasText(fromUserId)
                || !StringUtils.hasText(toUserId)
                || !StringUtils.hasText(text)
        ) {
            log.error("Один из аргментов пуст: fromUserId={}, toUserId={}, text={}", fromUserId, toUserId, text);
            throw new IllegalArgumentException("Один из аргументов пустой!");
        }

        return messageRepository.saveMessage(fromUserId, toUserId, text);
    }

    public List<MessageDto> getMessages(String fromUserId, String toUserId) {
        if (!StringUtils.hasText(fromUserId) || !StringUtils.hasText(toUserId)) {
            log.error("Один из пользователей не указан: fromUserId={}, toUserId={}", fromUserId, toUserId);
            throw new IllegalArgumentException("Один из пользователей нге указан!");
        }

        return messageRepository.findAllMessages(fromUserId, toUserId).stream()
                .map(messageMapper::toMessageDto)
                .toList();
    }
}
