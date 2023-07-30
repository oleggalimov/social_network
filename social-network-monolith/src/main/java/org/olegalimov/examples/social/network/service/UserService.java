package org.olegalimov.examples.social.network.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.olegalimov.examples.social.network.core.repository.UserRepository;
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
        log.info("Ищем пользователя с идентификатором {}.", userId);
        var entity = userRepository.findByUserId(userId);
        log.info("Найдена сущность с id={}", entity.getId());
        return userMapper.toUserDto(entity);
    }

    public String registerUser(UserDto userDto) {
        log.info("Регистрируем пользователя с именем {} {}", userDto.getFirstName(), userDto.getSecondName());
        var entity = userMapper.toUserEntity(userDto);
        return userRepository.saveUser(entity);
    }

    public List<UserDto> findByNames(String firstName, String secondName) {
        log.info("Ищем пользователя с именем {} и фамилией {}", firstName, secondName);
        return userRepository.findByNames(firstName, secondName).stream()
                .map(userMapper::toUserDto)
                .toList();
    }
}
