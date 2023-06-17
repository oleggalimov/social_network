package org.olegalimov.examples.social.network.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.olegalimov.examples.social.network.entity.Post;
import org.olegalimov.examples.social.network.exception.EntityNotFoundException;
import org.olegalimov.examples.social.network.exception.TooManyResultException;
import org.olegalimov.examples.social.network.mapper.jdbc.PostRowMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.RowMapperResultSetExtractor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.olegalimov.examples.social.network.constant.Queries.Delete.DELETE_POST_TEMPLATE;
import static org.olegalimov.examples.social.network.constant.Queries.Insert.INSERT_POST_TEMPLATE;
import static org.olegalimov.examples.social.network.constant.Queries.Select.SELECT_FRIENDS_FEED_TEMPLATE;
import static org.olegalimov.examples.social.network.constant.Queries.Select.SELECT_POST_BY_POST_ID_TEMPLATE;
import static org.olegalimov.examples.social.network.constant.Queries.Update.UPDATE_POST_TEMPLATE;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostsRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public boolean createPost(String postId, String authorUserId, String text) {
        var recordsCreated = jdbcTemplate.update(
                INSERT_POST_TEMPLATE,
                Map.of("postId", postId, "text", text, "authorUserId", authorUserId));
        return recordsCreated > 0;
    }

    public Post findByPostId(String postId) {
        var entityList = jdbcTemplate.query(
                SELECT_POST_BY_POST_ID_TEMPLATE,
                Map.of("postId", postId),
                new RowMapperResultSetExtractor<>(new PostRowMapper()));
        return switch (entityList.size()) {
            case 0 -> throw new EntityNotFoundException(postId);
            case 1 -> entityList.get(0);
            default -> throw new TooManyResultException(postId, entityList.size());
        };
    }

    public List<Post> getFeedForUser(String userId, int offset, int limit) {
        var entityList = jdbcTemplate.query(
                SELECT_FRIENDS_FEED_TEMPLATE,
                Map.of("userId", userId, "offset", offset, "limit", limit),
                new RowMapperResultSetExtractor<>(new PostRowMapper())
        );
        return entityList;
    }

    public boolean updatePost(String postId, String text, LocalDateTime postUpdateDateTime) {
        var recordsUpdated = jdbcTemplate.update(
                UPDATE_POST_TEMPLATE,
                Map.of("postId", postId, "text", text, "postUpdateDateTime", postUpdateDateTime));
        return recordsUpdated > 0;
    }

    public boolean deletePost(String postId) {
        var recordsDeleted = jdbcTemplate.update(
                DELETE_POST_TEMPLATE,
                Map.of("postId", postId));
        return recordsDeleted > 0;
    }
}
