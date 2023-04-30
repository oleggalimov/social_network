package org.olegalimov.examples.social.network.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.olegalimov.examples.social.network.dto.UserDto;
import org.olegalimov.examples.social.network.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING, unmappedTargetPolicy = ReportingPolicy.ERROR)
public abstract class UserMapper {

    @Autowired
    protected PasswordEncoder passwordEncoder;

    @Mapping(target = "password", ignore = true)
    @Mapping(target = "id", source = "userId")
    public abstract UserDto toUserDto(User userEntity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userId", expression = "java( generateUUid() )")
    @Mapping(target = "password", source = "password", qualifiedByName = "encodePassword")
    public abstract User toUserEntity(UserDto userDto);

    protected String generateUUid() {
        return UUID.randomUUID().toString();
    }

    @Named("encodePassword")
    protected String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }
}
