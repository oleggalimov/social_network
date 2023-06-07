package org.olegalimov.examples.social.network.dao;

import org.olegalimov.examples.social.network.entity.User;
import org.olegalimov.examples.social.network.exception.EntityNotFoundException;
import org.olegalimov.examples.social.network.exception.TooManyResultException;
import org.olegalimov.examples.social.network.mapper.jdbc.UserRowMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.RowMapperResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static org.olegalimov.examples.social.network.constant.Queries.Insert.INSERT_USER_TEMPLATE;
import static org.olegalimov.examples.social.network.constant.Queries.Select.SELECT_USERS_BY_NAMES;
import static org.olegalimov.examples.social.network.constant.Queries.Select.SELECT_USER_BY_USER_ID;

@Service
public class UserRepository {

    private final NamedParameterJdbcTemplate masterJdbcTemplate;
    private final NamedParameterJdbcTemplate slaveJdbcTemplate;

    public UserRepository(
            @Qualifier("masterJdbcTemplate") NamedParameterJdbcTemplate masterJdbcTemplate,
            @Qualifier("slaveJdbcTemplate") NamedParameterJdbcTemplate slaveJdbcTemplate) {
        this.masterJdbcTemplate = masterJdbcTemplate;
        this.slaveJdbcTemplate = slaveJdbcTemplate;
    }

    public User findByUserId(String userId) {
        return findByUserId(userId, false);
    }

    public User findByUserId(String userId, boolean fromSlave) {
        var userList = findAllByUserId(userId, fromSlave);
        return switch (userList.size()) {
            case 0 -> throw new EntityNotFoundException(userId);
            case 1 -> userList.get(0);
            default -> throw new TooManyResultException(userId, userList.size());
        };
    }

    public List<User> findAllByUserId(String userId, boolean fromSlave) {
        var template = fromSlave ? slaveJdbcTemplate : masterJdbcTemplate;

        return template.query(
                SELECT_USER_BY_USER_ID,
                Map.of("userId", userId),
                new RowMapperResultSetExtractor<>(new UserRowMapper()));
    }

    public String saveUser(User entity) {
        int result = masterJdbcTemplate.update(INSERT_USER_TEMPLATE, buildParametersMap(entity));
        if (result != 1) {
            throw new IllegalStateException("Не удалось сохранить пользователя с именем "
                    + entity.getFirstName() + " " + entity.getSecondName());
        }
        return entity.getUserId();
    }

    public List<User> findByNames(String firstName, String secondName, boolean fromSlave) {
        var template = fromSlave ? slaveJdbcTemplate : masterJdbcTemplate;

        return template.query(
                SELECT_USERS_BY_NAMES,
                Map.of(
                        "firstName", firstName,
                        "secondName", secondName),
                new RowMapperResultSetExtractor<>(new UserRowMapper())
        );
    }

    private MapSqlParameterSource buildParametersMap(User entity) {
        MapSqlParameterSource params = new MapSqlParameterSource();

        params.addValue("userId", entity.getUserId())
                .addValue("firstName", entity.getFirstName())
                .addValue("secondName", entity.getSecondName())
                .addValue("age", entity.getAge())
                .addValue("sex", entity.getSex())
                .addValue("interests", entity.getInterests())
                .addValue("city", entity.getCity())
                .addValue("password", entity.getPassword());

        return params;
    }
}
