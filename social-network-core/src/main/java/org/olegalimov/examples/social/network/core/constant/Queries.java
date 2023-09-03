package org.olegalimov.examples.social.network.core.constant;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Queries {

    public static class Insert {
        public static final String INSERT_USER_TEMPLATE = """
                INSERT INTO USERS (
                    user_id, first_name, second_name, age, sex, interests, city, password)
                values(:userId, :firstName, :secondName, :age, :sex, :interests, :city, :password);
                """;

        public static final String INSERT_FRIEND_TEMPLATE = """
                INSERT INTO friends(
                    user_id, friend_user_id)
                values(:userId, :friendUserId);
                """;
        public static final String INSERT_POST_TEMPLATE = """
                INSERT INTO posts(post_id, text, author_user_id)
                VALUES (:postId, :text, :authorUserId);
                """;
        public static final String INSERT_MESSAGE_TEMPLATE = """
                INSERT INTO messages(from_user_id, to_user_id, text)
                VALUES(:fromUserId, :toUserId, :text);
                """;
        public static final String UPSERT_UNREAD_MESSAGES_COUNT = """
                INSERT INTO unread_messages_counter (user_id, unread_counter)
                VALUES(:userId, :newCount)
                ON CONFLICT (user_id)
                  DO UPDATE SET unread_counter=:newCount, last_update_time=now();
                """;
    }

    public static class Select {
        public static final String SELECT_USER_BY_USER_ID = """
                SELECT * FROM USERS WHERE user_id = :userId;
                """;
        public static final String SELECT_USERS_BY_NAMES = """
                SELECT * FROM USERS WHERE first_name LIKE :firstName AND second_name LIKE :secondName
                """;
        public static final String SELECT_POST_BY_POST_ID_TEMPLATE = """
                SELECT * FROM posts
                WHERE post_id = :postId;
                """;
        public static final String SELECT_FRIENDS_FEED_TEMPLATE = """
                SELECT * FROM posts
                WHERE author_user_id IN (
                    SELECT friend_user_id from friends WHERE user_id = :userId)
                ORDER BY post_date_time DESC OFFSET :offset LIMIT :limit;
                """;
        public static final String SELECT_DIALOG_MESSAGES = """
                SELECT * FROM messages
                WHERE (from_user_id = :fromUserId OR from_user_id = :toUserId)
                    AND (to_user_id = :toUserId OR to_user_id = :fromUserId)
                ORDER BY message_date_time DESC;
                """;
        public static final String SELECT_UNREAD_MESSAGES = """
                SELECT * FROM messages
                WHERE to_user_id = :toUserId
                ORDER BY message_date_time DESC;
                """;
        public static final String SELECT_UNREAD_MSG_COUNT = """
                SELECT unread_counter FROM unread_messages_counter
                WHERE user_id = :userId;
                """;
    }

    public static class Update {
        public static final String UPDATE_POST_TEMPLATE = """
                UPDATE posts
                SET text=:text, post_update_time = :postUpdateDateTime
                WHERE post_id = :postId;
                """;
    }

    public static class Delete {
        public static final String DELETE_POST_TEMPLATE = """
                DELETE FROM posts
                WHERE post_id = :postId;
                """;
    }


}
