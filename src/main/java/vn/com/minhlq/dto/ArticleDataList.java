package vn.com.minhlq.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ArticleDataList {

    @JsonProperty("articles")
    private final List<ArticleData> articleDatas;

    @JsonProperty("articlesCount")
    private final int count;

}
