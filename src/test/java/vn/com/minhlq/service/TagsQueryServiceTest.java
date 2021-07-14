package vn.com.minhlq.service;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

import vn.com.minhlq.DbTestBase;
import vn.com.minhlq.model.Article;
import vn.com.minhlq.mybatis.repository.MyBatisArticleRepository;
import vn.com.minhlq.repository.ArticleRepository;

@Import({ TagsQueryService.class, MyBatisArticleRepository.class })
public class TagsQueryServiceTest extends DbTestBase {

    @Autowired
    private TagsQueryService tagsQueryService;

    @Autowired
    private ArticleRepository articleRepository;

    @Test
    public void should_get_all_tags() {
        articleRepository.save(new Article("test", "test", "test", Arrays.asList("java"), 123L));
        assertTrue(tagsQueryService.allTags().contains("java"));
    }

}
