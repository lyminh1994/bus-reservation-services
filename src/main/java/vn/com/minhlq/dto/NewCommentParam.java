package vn.com.minhlq.dto;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonRootName;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonRootName("comment")
public class NewCommentParam {

    @NotBlank(message = "Comment body can't be empty")
    private String body;

}
