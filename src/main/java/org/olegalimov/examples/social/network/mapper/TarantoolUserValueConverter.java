package org.olegalimov.examples.social.network.mapper;

import io.tarantool.driver.api.TarantoolClient;
import io.tarantool.driver.api.TarantoolResult;
import io.tarantool.driver.api.tuple.TarantoolTuple;
import io.tarantool.driver.mappers.MessagePackValueMapper;
import io.tarantool.driver.mappers.converters.ValueConverter;
import lombok.extern.slf4j.Slf4j;
import org.msgpack.value.ArrayValue;
import org.msgpack.value.Value;
import org.olegalimov.examples.social.network.dto.UserDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class TarantoolUserValueConverter implements ValueConverter<Value, List<UserDto>> {

    private final MessagePackValueMapper valueMapper;

    public TarantoolUserValueConverter(
            TarantoolClient<TarantoolTuple, TarantoolResult<TarantoolTuple>> tarantoolClient) {

        this.valueMapper = tarantoolClient.getConfig().getMessagePackMapper();
    }

    @Override
    public List<UserDto> fromValue(Value value) {
        if (value == null) {
            return List.of();
        }
        var result = new ArrayList<UserDto>();
        for (var arrayElement : value.asArrayValue()) {
            ArrayValue arrayValue = arrayElement.asArrayValue();
            UserDto dto = new UserDto(
                    arrayValue.get(1).toString(),
                    null,
                    arrayValue.get(4).toString(),
                    arrayValue.get(3).toString(),
                    arrayValue.get(5).asIntegerValue().asInt(),
                    arrayValue.get(6).toString(),
                    arrayValue.get(7).toString(),
                    arrayValue.get(8).toString()
            );
            result.add(dto);
        }
        return result;
    }

    @Override
    public boolean canConvertValue(Value value) {
        return value != null && value.isArrayValue();
    }
}
