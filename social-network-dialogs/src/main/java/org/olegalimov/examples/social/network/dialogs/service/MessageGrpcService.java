package org.olegalimov.examples.social.network.dialogs.service;

import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import org.olegalimov.examples.social.network.dialogs.grpc.FindAllMessagesResponse;
import org.olegalimov.examples.social.network.dialogs.grpc.Message;
import org.olegalimov.examples.social.network.dialogs.grpc.MessageServiceGrpc;
import org.olegalimov.examples.social.network.dialogs.grpc.SaveMessageResponse;

@GrpcService
@RequiredArgsConstructor
public class MessageGrpcService extends MessageServiceGrpc.MessageServiceImplBase {

    private final MessageService messageService;

    @Override
    public void saveMessage(Message request, StreamObserver<SaveMessageResponse> responseObserver) {
        var result = messageService.saveMessage(request.getFromUserId(), request.getToUserId(), request.getText());
        responseObserver.onNext(SaveMessageResponse.newBuilder()
                .setSuccess(result)
                .build());
        responseObserver.onCompleted();
    }

    @Override
    public void findAllMessages(Message request, StreamObserver<FindAllMessagesResponse> responseObserver) {
        var result = messageService.getMessages(request.getFromUserId(), request.getToUserId());
        responseObserver.onNext(FindAllMessagesResponse.newBuilder()
                .addAllItems(result)
                .build());
        responseObserver.onCompleted();
    }
}
