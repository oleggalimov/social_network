package org.olegalimov.examples.social.network.core.repository;

import lombok.RequiredArgsConstructor;
import org.olegalimov.examples.social.network.core.constant.Queries;
import org.olegalimov.examples.social.network.core.entity.User;
import org.olegalimov.examples.social.network.core.exception.EntityNotFoundException;
import org.olegalimov.examples.social.network.core.exception.TooManyResultException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.RowMapperResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static org.olegalimov.examples.social.network.core.constant.Queries.Select.SELECT_USERS_BY_NAMES;

@Service
@RequiredArgsConstructor
public class UserRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final RowMapper<User> userRowMapper;

    public User findByUserId(String userId) {
        var userList = findAllByUserId(userId);
        return switch (userList.size()) {
            case 0 -> throw new EntityNotFoundException(userId);
            case 1 -> userList.get(0);
            default -> throw new TooManyResultException(userId, userList.size());
        };
    }

    public List<User> findAllByUserId(String userId) {

        return jdbcTemplate.query(
                Queries.Select.SELECT_USER_BY_USER_ID,
                Map.of("userId", userId),
                new RowMapperResultSetExtractor<>(userRowMapper));
    }

    public String saveUser(User entity) {
        int result = jdbcTemplate.update(Queries.Insert.INSERT_USER_TEMPLATE, buildParametersMap(entity));
        if (result != 1) {
            throw new IllegalStateException("Не удалось сохранить пользователя с именем "
                    + entity.getFirstName() + " " + entity.getSecondName());
        }
        return entity.getUserId();
    }

    public List<User> findByNames(String firstName, String secondName) {

        return jdbcTemplate.query(
                SELECT_USERS_BY_NAMES,
                Map.of(
                        "firstName", firstName,
                        "secondName", secondName),
                new RowMapperResultSetExtractor<>(userRowMapper)
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
