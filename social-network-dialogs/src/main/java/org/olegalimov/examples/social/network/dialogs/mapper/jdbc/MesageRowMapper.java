package org.olegalimov.examples.social.network.dialogs.mapper.jdbc;

import org.olegalimov.examples.social.network.core.entity.MessageEntity;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;

@Service
public class MesageRowMapper implements RowMapper<MessageEntity> {
    @Override
    public MessageEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new MessageEntity(
                rs.getLong("id"),
                rs.getString("from_user_id"),
                rs.getString("to_user_id"),
                rs.getString("text"),
                rs.getTimestamp("message_date_time").toInstant()
        );
    }
}