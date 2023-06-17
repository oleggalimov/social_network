package org.olegalimov.examples.social.network.mapper.jdbc;

import org.olegalimov.examples.social.network.entity.Post;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class PostRowMapper implements RowMapper<Post> {
    @Override
    public Post mapRow(ResultSet rs, int rowNum) throws SQLException {
        var updateTime = rs.getTimestamp("post_update_time") == null
                ? null : rs.getTimestamp("post_update_time").toLocalDateTime();
        return new Post(
                rs.getLong("id"),
                rs.getString("post_id"),
                rs.getString("text"),
                rs.getString("author_user_id"),
                rs.getTimestamp("post_date_time").toLocalDateTime(),
                updateTime
        );
    }
}