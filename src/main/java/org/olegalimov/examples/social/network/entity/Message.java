package org.olegalimov.examples.social.network.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Accessors(chain = true)
public class Message {
    private Long id;
    private String fromUserId;
    private String toUserId;
    private String text;
    private LocalDateTime messageDateTime;
}
