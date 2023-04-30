package org.olegalimov.examples.social.network.constant;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Queries {

    public static class Insert {
        public static final String INSERT_USER_TEMPLATE = """
                INSERT INTO USERS (
                    user_id, first_name, second_name, birth_date, biography, city, password)
                values(:userId, :firstName, :secondName, :birthDate, :biography, :city, :password);
                """;
    }

    public static class Select {
        public static final String SELECT_USER_BY_USER_ID = """
                SELECT * FROM USERS WHERE user_id = :userId;
                """;
        public static final String SELECT_USERS_BY_NAMES = """
                SELECT * FROM USERS WHERE first_name LIKE :firstName AND second_name LIKE :secondName
                """;
    }

}
