package org.olegalimov.examples.social.network.constant;

import lombok.experimental.UtilityClass;

@UtilityClass
public class CommonConstant {
    public static final String LOCAL_DATE_FORMAT = "yyyy-MM-dd";
    public static final String POSTS_CACHE_NAME = "postsCache";

    public static class WebSocket {
        public static final String WS_ENDPOINT = "/ws";
        public static final String WS_EXCHANGE_PREFIX = "/exchange";
        public static final String WS_EXCHANGE_POST_PREFIX = WS_EXCHANGE_PREFIX + "/post/";
    }
}
