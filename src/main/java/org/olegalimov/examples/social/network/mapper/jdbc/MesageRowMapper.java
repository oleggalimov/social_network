package org.olegalimov.examples.social.network.mapper.jdbc;

import org.olegalimov.examples.social.network.entity.Message;
import org.olegalimov.examples.social.network.entity.Post;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class MesageRowMapper implements RowMapper<Message> {
    @Override
    public Message mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Message(
                rs.getLong("id"),
                rs.getString("from_user_id"),
                rs.getString("to_user_id"),
                rs.getString("text"),
                rs.getTimestamp("message_date_time").toLocalDateTime()
        );
    }
}