package org.olegalimov.examples.social.network.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import static org.olegalimov.examples.social.network.constant.CommonConstant.WebSocket.WS_ENDPOINT;
import static org.olegalimov.examples.social.network.constant.CommonConstant.WebSocket.WS_EXCHANGE_PREFIX;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfiguration implements WebSocketMessageBrokerConfigurer {

    @Value("${spring.rabbitmq.host}")
    private String relayHost;
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry
                .setApplicationDestinationPrefixes("/social-network")
                .setUserDestinationPrefix("/user")
                .enableStompBrokerRelay(WS_EXCHANGE_PREFIX)
                .setClientLogin("guest")
                .setClientPasscode("guest")
                .setRelayHost(relayHost)
                .setRelayPort(61613);
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
