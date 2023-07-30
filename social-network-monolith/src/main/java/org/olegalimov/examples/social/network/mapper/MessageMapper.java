package org.olegalimov.examples.social.network.mapper;

import com.google.protobuf.Timestamp;
import org.olegalimov.examples.social.network.dialogs.grpc.Message;
import org.olegalimov.examples.social.network.dto.MessageDto;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class MessageMapper {

    public Message toMessage(String fromUserId, String toUserId, String text) {
        var currentTimeInstant = Instant.now();
        return Message.newBuilder()
                .setFromUserId(fromUserId)
                .setToUserId(toUserId)
                .setText(text == null ? "" : text)
                .setTimestamp(Timestamp.newBuilder()
                        .setSeconds(currentTimeInstant.getEpochSecond())
                        .setNanos(currentTimeInstant.getNano())
                        .build())
                .build();
    }

    public Message toMessage(String fromUserId, String toUserId) {
        return toMessage(fromUserId, toUserId, null);
    }

    public MessageDto toMessageDto(Message message) {
        var messageTimestamp = message.getTimestamp();
        var timestamp = Instant.ofEpochSecond(messageTimestamp.getSeconds(), messageTimestamp.getNanos());
        return new MessageDto(
                message.getFromUserId(),
                message.getToUserId(),
                message.getText(),
                timestamp);
    }


}
