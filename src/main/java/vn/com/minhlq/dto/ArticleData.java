package vn.com.minhlq.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.joda.time.DateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.com.minhlq.common.DateTimeCursor;
import vn.com.minhlq.common.INode;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleData implements INode {

    private Long id;

    private String slug;

    private String title;

    private String description;

    private String body;

    private boolean favorited;

    private int favoritesCount;

    private DateTime createdAt;

    private DateTime updatedAt;

    private List<String> tagList;

    @JsonProperty("author")
    private ProfileData profileData;

    @Override
    public DateTimeCursor getCursor() {
        return new DateTimeCursor(updatedAt);
    }

}
