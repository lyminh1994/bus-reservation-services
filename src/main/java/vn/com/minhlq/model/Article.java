package vn.com.minhlq.model;

import static java.util.stream.Collectors.toList;

import java.util.HashSet;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Article {

    private Long id;

    private Long userId;

    private String slug;

    private String title;

    private String description;

    private String body;

    private List<Tag> tags;

    private DateTime createdAt;

    private DateTime updatedAt;

    public Article(String title, String description, String body, List<String> tagList, Long userId) {
        this(title, description, body, tagList, userId, new DateTime());
    }

    public Article(String title, String description, String body, List<String> tagList, Long userId,
            DateTime createdAt) {
        this.slug = toSlug(title);
        this.title = title;
        this.description = description;
        this.body = body;
        this.tags = new HashSet<>(tagList).stream().map(Tag::new).collect(toList());
        this.userId = userId;
        this.createdAt = createdAt;
        this.updatedAt = createdAt;
    }

    public void update(String title, String description, String body) {
        if (!StringUtils.isEmpty(title)) {
            this.title = title;
            this.slug = toSlug(title);
            this.updatedAt = new DateTime();
        }
        if (!StringUtils.isEmpty(description)) {
            this.description = description;
            this.updatedAt = new DateTime();
        }
        if (!StringUtils.isEmpty(body)) {
            this.body = body;
            this.updatedAt = new DateTime();
        }
    }

    public static String toSlug(String title) {
        return title.toLowerCase().replaceAll("[\\&|[\\uFE30-\\uFFA0]|\\’|\\”|\\s\\?\\,\\.]+", "-");
    }

}
