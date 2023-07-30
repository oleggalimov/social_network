package org.olegalimov.examples.social.network.core.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@Accessors(chain = true)
public class User {
    private Long id;
    private String userId;
    private String password;
    private String firstName;
    private String secondName;
    private Integer age;
    private String sex;
    private String interests;
    private String city;
}
