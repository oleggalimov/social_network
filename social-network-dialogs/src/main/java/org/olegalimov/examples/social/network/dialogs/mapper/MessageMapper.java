package org.olegalimov.examples.social.network.dialogs.mapper;

import com.google.protobuf.Timestamp;
import org.olegalimov.examples.social.network.core.entity.MessageEntity;
import org.olegalimov.examples.social.network.dialogs.grpc.Message;
import org.springframework.stereotype.Service;

@Service
public class MessageMapper {
    public Message toProtobufMessage(MessageEntity message) {
        return Message.newBuilder()
                .setId(message.getId())
                .setFromUserId(message.getFromUserId())
                .setToUserId(message.getToUserId())
                .setText(message.getText())
                .setTimestamp(Timestamp.newBuilder()
                        .setSeconds(message.getMessageDateTime().getEpochSecond())
                        .setNanos(message.getMessageDateTime().getNano())
                        .build())
                .build();
    }
}
