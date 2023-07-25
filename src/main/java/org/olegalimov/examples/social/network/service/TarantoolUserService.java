package org.olegalimov.examples.social.network.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.olegalimov.examples.social.network.dto.UserDto;
import org.olegalimov.examples.social.network.entity.tarantool.TarantoolUser;
import org.olegalimov.examples.social.network.mapper.UserMapper;
import org.springframework.data.tarantool.core.TarantoolTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class TarantoolUserService {

    private static final String FIND_USERS_FUNCTION_NAME = "find_users";

    private final TarantoolTemplate tarantoolTemplate;
    private final UserMapper userMapper;

    public List<UserDto> findByNames(String firstName, String lastName) {
        var usersList = tarantoolTemplate.callForTupleList(
                FIND_USERS_FUNCTION_NAME, new Object [] {firstName, lastName}, TarantoolUser.class);
        log.info("В базе тарантула найдено {} записей.", usersList.size());

        return usersList.stream()
                .map(userMapper::toUserDto)
                .toList();
    }
}
