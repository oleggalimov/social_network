package org.olegalimov.examples.social.network.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import static org.olegalimov.examples.social.network.constant.CommonConstant.WebSocket.WS_ENDPOINT;
import static org.olegalimov.examples.social.network.constant.CommonConstant.WebSocket.WS_EXCHANGE_PREFIX;

@Configuration
@EnableWebSocketMessageBroker
@Slf4j
public class WebSocketConfiguration implements WebSocketMessageBrokerConfigurer {

    @Value("${spring.rabbitmq.host}")
    private String relayHost;
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        log.info("Настраиваем месседж-брокера...");
        registry
                .setApplicationDestinationPrefixes("/social-network")
                .setUserDestinationPrefix("/user");
        if(!StringUtils.hasLength(relayHost)) {
            log.warn("Запуск локального брокера, так как не указан хост rabbit mq!");
            registry.enableSimpleBroker(WS_EXCHANGE_PREFIX);
        } else {
            log.warn("Запуск брокера mq {}", relayHost);
            registry.enableStompBrokerRelay(WS_EXCHANGE_PREFIX)
                    .setClientLogin("guest")
                    .setClientPasscode("guest")
                    .setRelayHost(relayHost)
                    .setRelayPort(61613);
        }

    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        //добавляем перевалочный эндпойнт, f.ex.: http://localhost:8080/ws
        //после этого можно будеть запросить соединение по этому адресу по http://
        //и проапгрейдить его до ws://
        registry.addEndpoint(WS_ENDPOINT)
                .setAllowedOriginPatterns("*")
                .withSockJS();
    }
}
