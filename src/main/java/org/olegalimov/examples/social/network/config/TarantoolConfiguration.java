package org.olegalimov.examples.social.network.config;

import io.tarantool.driver.api.TarantoolClientConfig;
import io.tarantool.driver.api.TarantoolServerAddress;
import io.tarantool.driver.auth.SimpleTarantoolCredentials;
import io.tarantool.driver.auth.TarantoolCredentials;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.tarantool.config.AbstractTarantoolDataConfiguration;

@Configuration
public class TarantoolConfiguration extends AbstractTarantoolDataConfiguration {

    @Value("${tarantool.host}")
    protected String host;

    @Value("${tarantool.port}")
    protected int port;

    @Value("${tarantool.username}")
    protected String username;

    @Value("${tarantool.password}")
    protected String password;

    @Override
    protected void configureClientConfig(TarantoolClientConfig.Builder builder) {
        builder.withConnectTimeout(1000 * 5).withReadTimeout(1000 * 5).withRequestTimeout(1000 * 5);
    }

    @Override
    public TarantoolCredentials tarantoolCredentials() {
        return new SimpleTarantoolCredentials(username, password);
    }

    @Override
    protected TarantoolServerAddress tarantoolServerAddress() {
        return new TarantoolServerAddress(host, port);
    }
}
