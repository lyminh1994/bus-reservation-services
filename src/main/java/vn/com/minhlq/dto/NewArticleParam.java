package vn.com.minhlq.dto;

import java.util.List;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonRootName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import vn.com.minhlq.validation.DuplicatedArticleConstraint;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonRootName("article")
public class NewArticleParam {

    @NotBlank(message = "Article title can't be empty")
    @DuplicatedArticleConstraint
    private String title;

    @NotBlank(message = "Article description can't be empty")
    private String description;

    @NotBlank(message = "Article body can't be empty")
    private String body;

    private List<String> tagList;

}
