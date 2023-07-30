package org.olegalimov.examples.social.network.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.ToString;

@Data
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
public class TarantoolUserDto {

    private String id;
    private String uuid;
    private String password;
    private String firstName;
    private String secondName;
    private Integer age;
    private String sex;
    private String interests;
    private String city;


}
