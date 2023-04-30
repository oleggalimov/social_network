package org.olegalimov.examples.social.network.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class User {
    private Long id;
    private String userId;
    private String firstName;
    private String secondName;
    private LocalDate birthDate;
    private String biography;
    private String city;
    private String password;
}
