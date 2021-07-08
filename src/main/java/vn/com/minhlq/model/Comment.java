package vn.com.minhlq.model;

import java.util.Date;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class Comment {

    private Long id;

    private String body;

    private Long userId;

    private Long articleId;

    private Long createdAt;

    public Comment(String body, Long userId, Long articleId) {
        this.body = body;
        this.userId = userId;
        this.articleId = articleId;
        this.createdAt = new Date().getTime();
    }

}
