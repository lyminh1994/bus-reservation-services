package vn.com.minhlq.model;

import org.joda.time.DateTime;

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

    private DateTime createdAt;

    public Comment(String body, Long userId, Long articleId) {
        this.body = body;
        this.userId = userId;
        this.articleId = articleId;
        this.createdAt = new DateTime();
    }

}
