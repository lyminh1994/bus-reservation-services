package vn.com.minhlq.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class CommentData implements INode {

    private Long id;

    private String body;

    @JsonIgnore
    private Long articleId;

    private DateTime createdAt;

    private DateTime updatedAt;

    @JsonProperty("author")
    private ProfileData profileData;

    @Override
    public DateTimeCursor getCursor() {
        return new DateTimeCursor(createdAt);
    }

}
