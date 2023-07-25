package org.olegalimov.examples.social.network.config;

import io.tarantool.driver.api.*;
import io.tarantool.driver.api.tuple.TarantoolTuple;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TarantoolConfiguration {

    @Value("${tarantool.host}")
    protected String host;

    @Value("${tarantool.port}")
    protected int port;

    @Value("${tarantool.username}")
    protected String username;

    @Value("${tarantool.password}")
    protected String password;

    @Bean
    public TarantoolClient<TarantoolTuple, TarantoolResult<TarantoolTuple>> tarantoolClient() {
        return TarantoolClientFactory.createClient()
                .withAddress(host, port)
                .withCredentials(username, password)
                // Specify using the default CRUD proxy operations mapping configuration
                .withProxyMethodMapping()
                // You may also specify more client settings, such as:
                // timeouts, number of connections, custom MessagePack entities to Java objects mapping, etc.
                .build();
    }
}
