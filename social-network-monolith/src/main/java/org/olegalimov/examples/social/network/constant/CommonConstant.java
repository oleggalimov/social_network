package org.olegalimov.examples.social.network.constant;

import io.grpc.Metadata;
import lombok.experimental.UtilityClass;

import static io.grpc.Metadata.ASCII_STRING_MARSHALLER;

@UtilityClass
public class CommonConstant {
    public static final String POSTS_CACHE_NAME = "postsCache";
    public static final Metadata.Key<String> AUTHORIZATION_METADATA_KEY =
            Metadata.Key.of("Authorization", ASCII_STRING_MARSHALLER);

    public static class WebSocket {
        public static final String WS_ENDPOINT = "/ws";
        public static final String WS_EXCHANGE_PREFIX = "/exchange";
        public static final String WS_EXCHANGE_POST_PREFIX = WS_EXCHANGE_PREFIX + "/post/";
    }
}
