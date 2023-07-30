package org.olegalimov.examples.social.network.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Accessors(chain = true)
public class Post {
    private Long id;
    private String postId;
    private String text;
    private String authorUserId;
    private LocalDateTime postCreatedAt;
    private LocalDateTime postUpdatedAt;
}
