package org.olegalimov.examples.social.network.core.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.Instant;

@Data
@AllArgsConstructor
@Accessors(chain = true)
public class MessageEntity {
    private Long id;
    private String fromUserId;
    private String toUserId;
    private String text;
    private Instant messageDateTime;
}
