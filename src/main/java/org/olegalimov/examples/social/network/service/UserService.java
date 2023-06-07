package org.olegalimov.examples.social.network.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.olegalimov.examples.social.network.dao.UserRepository;
import org.olegalimov.examples.social.network.dto.UserDto;
import org.olegalimov.examples.social.network.mapper.UserMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserDto findByUserId(String userId) {
        return findByUserId(userId, false);
    }

    public UserDto findByUserId(String userId, boolean fromSlave) {
        log.info("Ищем пользователя с идентификатором {}, признак поиска на слейве: {}", userId, fromSlave);
        var entity = userRepository.findByUserId(userId, fromSlave);
        log.info("Найдена сущность с id={}", entity.getId());
        return userMapper.toUserDto(entity);
    }

    public String registerUser(UserDto userDto) {
        log.info("Регистрируем пользователя с именем {} {}", userDto.getFirstName(), userDto.getSecondName());
        var entity = userMapper.toUserEntity(userDto);
        return userRepository.saveUser(entity);
    }

    public List<UserDto> findByNames(String firstName, String secondName) {
        return findByNames(firstName, secondName, false);
    }

    public List<UserDto> findByNames(String firstName, String secondName, boolean fromSlave) {
        log.info("Ищем пользователя с именем {} и фамилией {}", firstName, secondName);
        return userRepository.findByNames(firstName, secondName, fromSlave).stream()
                .map(userMapper::toUserDto)
                .toList();
    }
}
