package org.olegalimov.examples.social.network.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.olegalimov.examples.social.network.dialogs.grpc.MessageServiceGrpc;
import org.olegalimov.examples.social.network.dto.MessageDto;
import org.olegalimov.examples.social.network.mapper.MessageMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageMapper messageMapper;

    private final MessageServiceGrpc.MessageServiceBlockingStub messageServiceBlockingStub;

    public boolean saveMessage(String fromUserId, String toUserId, String text) {
        if (!StringUtils.hasText(fromUserId)
                || !StringUtils.hasText(toUserId)
                || !StringUtils.hasText(text)
        ) {
            log.error("Один из аргментов пуст: fromUserId={}, toUserId={}, text={}", fromUserId, toUserId, text);
            throw new IllegalArgumentException("Один из аргументов пустой!");
        }

        return messageServiceBlockingStub
                .saveMessage(messageMapper.toMessage(fromUserId, toUserId, text))
                .getSuccess();
    }

    public List<MessageDto> getMessages(String fromUserId, String toUserId) {
        if (!StringUtils.hasText(fromUserId) || !StringUtils.hasText(toUserId)) {
            log.error("Один из пользователей не указан: fromUserId={}, toUserId={}", fromUserId, toUserId);
            throw new IllegalArgumentException("Один из пользователей не указан!");
        }

        return messageServiceBlockingStub.findAllMessages(messageMapper.toMessage(fromUserId, toUserId))
                .getItemsList()
                .stream()
                .map(messageMapper::toMessageDto)
                .toList();
    }
}
