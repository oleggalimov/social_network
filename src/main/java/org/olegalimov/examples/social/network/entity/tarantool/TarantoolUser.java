package org.olegalimov.examples.social.network.entity.tarantool;

import lombok.Data;
import org.springframework.data.tarantool.core.mapping.Field;
import org.springframework.data.tarantool.core.mapping.Tuple;

@Tuple("users")
@Data
public class TarantoolUser {
    @Field(name = "id")
    private Long id;

    @Field(name = "user_id")
    private String userId;

    @Field(name = "password")
    private String password;

    @Field(name = "first_name")
    private String firstName;

    @Field(name = "second_name")
    private String secondName;

    @Field(name = "age")
    private Integer age;

    @Field(name = "sex")
    private String sex;

    @Field(name = "interests")
    private String interests;

    @Field(name = "city")
    private String city;
}
