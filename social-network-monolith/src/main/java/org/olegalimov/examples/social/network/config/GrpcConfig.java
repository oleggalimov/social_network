package org.olegalimov.examples.social.network.config;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.extern.slf4j.Slf4j;
import org.olegalimov.examples.social.network.credentials.BasicAuthCredentials;
import org.olegalimov.examples.social.network.dialogs.grpc.MessageServiceGrpc;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class GrpcConfig {

    @Value("${grpc.message-client.host}")
    private String host;

    @Value("${grpc.message-client.port}")
    private int port;

    @Value("${grpc.message-client.username}")
    private String username;

    @Value("${grpc.message-client.password}")
    private String password;


    @Bean
    public MessageServiceGrpc.MessageServiceBlockingStub messageServiceBlockingStub() {
        log.info("Конфигурация подключения к grpc: host={}, port={}", host, port);
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress(host, port)
                .usePlaintext()
                .build();
        return MessageServiceGrpc.newBlockingStub(channel)
                .withCallCredentials(new BasicAuthCredentials(username, password));
    }
}
