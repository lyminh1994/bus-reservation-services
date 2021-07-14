package vn.com.minhlq.mybatis.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

import vn.com.minhlq.DbTestBase;
import vn.com.minhlq.model.Article;
import vn.com.minhlq.model.Tag;
import vn.com.minhlq.model.User;
import vn.com.minhlq.repository.ArticleRepository;
import vn.com.minhlq.repository.UserRepository;

@Import({ MyBatisArticleRepository.class, MyBatisFollowRepository.class })
public class MyBatisArticleRepositoryTest extends DbTestBase {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private UserRepository userRepository;

    private Article article;

    @BeforeEach
    public void setUp() {
        User user = new User("aisensiy", "123", "0911", "aisensiy@gmail.com");
        userRepository.save(user);
        article = new Article("test", "desc", "body", Arrays.asList("java", "spring"), user.getId());
    }

    @Test
    public void should_create_and_fetch_article_success() {
        articleRepository.save(article);
        Optional<Article> optional = articleRepository.findById(article.getId());
        assertTrue(optional.isPresent());
        assertEquals(optional.get(), article);
        assertTrue(optional.get().getTags().contains(new Tag("java")));
        assertTrue(optional.get().getTags().contains(new Tag("spring")));
    }

    @Test
    public void should_update_and_fetch_article_success() {
        articleRepository.save(article);

        String newTitle = "new test 2";
        article.update(newTitle, "", "");
        articleRepository.save(article);
        System.out.println(article.getSlug());
        Optional<Article> optional = articleRepository.findBySlug(article.getSlug());
        assertTrue(optional.isPresent());
        Article fetched = optional.get();
        assertEquals(fetched.getTitle(), newTitle);
        assertNotEquals(fetched.getBody(), "");
    }

    @Test
    public void should_delete_article() {
        articleRepository.save(article);

        articleRepository.remove(article);
        assertFalse(articleRepository.findById(article.getId()).isPresent());
    }

}
