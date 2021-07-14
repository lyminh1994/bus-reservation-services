package vn.com.minhlq.api;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import vn.com.minhlq.config.JacksonConfig;
import vn.com.minhlq.dto.ArticleData;
import vn.com.minhlq.dto.ProfileData;
import vn.com.minhlq.model.Article;
import vn.com.minhlq.model.ArticleFavorite;
import vn.com.minhlq.model.Tag;
import vn.com.minhlq.model.User;
import vn.com.minhlq.repository.ArticleFavoriteRepository;
import vn.com.minhlq.repository.ArticleRepository;
import vn.com.minhlq.security.SecurityConfig;
import vn.com.minhlq.service.ArticleQueryService;

@WebMvcTest(ArticleFavoriteApi.class)
@Import({ SecurityConfig.class, JacksonConfig.class })
public class ArticleFavoriteApiTest extends TestWithCurrentUser {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ArticleFavoriteRepository articleFavoriteRepository;

    @MockBean
    private ArticleRepository articleRepository;

    @MockBean
    private ArticleQueryService articleQueryService;

    private Article article;

    @BeforeEach
    public void setUp() throws Exception {
        super.setUp();
        RestAssuredMockMvc.mockMvc(mvc);
        User anotherUser = new User("other", "123", "0911", "other@test.com");
        article = new Article("title", "desc", "body", Arrays.asList("java"), anotherUser.getId());
        when(articleRepository.findBySlug(eq(article.getSlug()))).thenReturn(Optional.of(article));
        ArticleData articleData = new ArticleData(article.getId(), article.getSlug(), article.getTitle(),
                article.getDescription(), article.getBody(), true, 1, article.getCreatedAt(), article.getUpdatedAt(),
                article.getTags().stream().map(Tag::getName).collect(Collectors.toList()),
                new ProfileData(anotherUser.getId(), anotherUser.getUsername(), false));
        when(articleQueryService.findBySlug(eq(articleData.getSlug()), eq(user))).thenReturn(Optional.of(articleData));
    }

    @Test
    public void should_favorite_an_article_success() throws Exception {
        given().header("Authorization", "Token " + token).when().post("/articles/{slug}/favorite", article.getSlug())
                .prettyPeek().then().statusCode(200).body("article.id", equalTo(article.getId()));

        verify(articleFavoriteRepository).save(any());
    }

    @Test
    public void should_unfavorite_an_article_success() throws Exception {
        when(articleFavoriteRepository.find(eq(article.getId()), eq(user.getId())))
                .thenReturn(Optional.of(new ArticleFavorite(article.getId(), user.getId())));
        given().header("Authorization", "Token " + token).when().delete("/articles/{slug}/favorite", article.getSlug())
                .prettyPeek().then().statusCode(200).body("article.id", equalTo(article.getId()));
        verify(articleFavoriteRepository).remove(new ArticleFavorite(article.getId(), user.getId()));
    }

}
