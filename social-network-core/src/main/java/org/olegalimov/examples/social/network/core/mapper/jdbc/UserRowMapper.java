package org.olegalimov.examples.social.network.core.mapper.jdbc;

import org.olegalimov.examples.social.network.core.entity.User;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;

@Service
public class UserRowMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new User(
                rs.getLong("id"),
                rs.getString("user_id"),
                rs.getString("password"),
                rs.getString("first_name"),
                rs.getString("second_name"),
                rs.getInt("age"),
                rs.getString("sex"),
                rs.getString("interests"),
                rs.getString("city")
        );
    }
}