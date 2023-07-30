package org.olegalimov.examples.social.network.service;

import io.tarantool.driver.api.SingleValueCallResult;
import io.tarantool.driver.api.TarantoolClient;
import io.tarantool.driver.api.TarantoolResult;
import io.tarantool.driver.api.tuple.TarantoolTuple;
import io.tarantool.driver.mappers.CallResultMapper;
import io.tarantool.driver.mappers.converters.ValueConverter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.msgpack.value.Value;
import org.olegalimov.examples.social.network.dto.UserDto;
import org.olegalimov.examples.social.network.dto.UserDtoCallResult;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class TarantoolUserService {
    private static final long DEFAULT_FUTURE_TIMEOUT_SEC = 5;
    private static final String FIND_USERS_FUNCTION_NAME = "find_users";

    private final TarantoolClient<TarantoolTuple, TarantoolResult<TarantoolTuple>> tarantoolClient;
    private final CallResultMapper<List<UserDto>, SingleValueCallResult<List<UserDto>>> userMapper;

    public TarantoolUserService(TarantoolClient<TarantoolTuple, TarantoolResult<TarantoolTuple>> tarantoolClient, ValueConverter<Value, List<UserDto>> valueConverter) {
        this.tarantoolClient = tarantoolClient;
        userMapper = tarantoolClient
                .getResultMapperFactoryFactory().<List<UserDto>>singleValueResultMapperFactory()
                .withSingleValueResultConverter(valueConverter, UserDtoCallResult.class);

    }

    @SneakyThrows
    public List<UserDto> findByNames(String firstName, String lastName) {
        List<UserDto> result = tarantoolClient.callForSingleResult(
                        FIND_USERS_FUNCTION_NAME,
                        List.of(firstName, lastName),
                        userMapper)
                .get(DEFAULT_FUTURE_TIMEOUT_SEC, TimeUnit.SECONDS);
        log.info("В тарантуле найдено {} записей", result.size());
        return result;
    }
}
