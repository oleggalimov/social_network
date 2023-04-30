package org.olegalimov.examples.social.network.mapper.jdbc;

import org.olegalimov.examples.social.network.entity.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class UserRowMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new User(
                rs.getLong("id"),
                rs.getString("user_id"),
                rs.getString("first_name"),
                rs.getString("second_name"),
                rs.getDate("birth_date").toLocalDate(),
                rs.getString("biography"),
                rs.getString("city"),
                rs.getString("password")
        );
    }
}