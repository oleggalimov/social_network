package org.olegalimov.examples.social.network.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.olegalimov.examples.social.network.entity.Message;
import org.olegalimov.examples.social.network.mapper.jdbc.MesageRowMapper;
import org.springframework.jdbc.core.RowMapperResultSetExtractor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static org.olegalimov.examples.social.network.constant.Queries.Insert.INSERT_MESSAGE_TEMPLATE;
import static org.olegalimov.examples.social.network.constant.Queries.Select.SELECT_DIALOG_MESSAGES;

@Service
@Slf4j
@RequiredArgsConstructor
public class MessageRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;


    public boolean saveMessage(String fromUserId, String toUserId, String text) {

        return jdbcTemplate.update(
                INSERT_MESSAGE_TEMPLATE,
                Map.of("fromUserId", fromUserId, "toUserId", toUserId, "text", text)) > 0;
    }

    public List<Message> findAllMessages(String fromUserId, String toUserId) {
        return jdbcTemplate.query(
                SELECT_DIALOG_MESSAGES,
                Map.of("fromUserId", fromUserId, "toUserId", toUserId),
                new RowMapperResultSetExtractor<>(new MesageRowMapper())
        );
    }
}
