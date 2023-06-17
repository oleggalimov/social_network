package org.olegalimov.examples.social.network.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

import static org.olegalimov.examples.social.network.constant.Queries.Insert.INSERT_FRIEND_TEMPLATE;

@Service
@Slf4j
@RequiredArgsConstructor
public class FriendRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;


    public boolean setFriends(String userId, String friendUserId) {

        return jdbcTemplate.update(
                INSERT_FRIEND_TEMPLATE,
                Map.of("userId", userId, "friendUserId", friendUserId)) > 0;
    }
}
