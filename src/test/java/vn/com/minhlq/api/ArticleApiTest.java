package vn.com.minhlq.api;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import vn.com.minhlq.TestHelper;
import vn.com.minhlq.config.JacksonConfig;
import vn.com.minhlq.dto.ArticleData;
import vn.com.minhlq.dto.ProfileData;
import vn.com.minhlq.model.Article;
import vn.com.minhlq.model.User;
import vn.com.minhlq.repository.ArticleRepository;
import vn.com.minhlq.security.SecurityConfig;
import vn.com.minhlq.service.ArticleCommandService;
import vn.com.minhlq.service.ArticleQueryService;

@WebMvcTest({ ArticleApi.class })
@Import({ SecurityConfig.class, JacksonConfig.class })
public class ArticleApiTest extends TestWithCurrentUser {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ArticleQueryService articleQueryService;

    @MockBean
    private ArticleRepository articleRepository;

    @MockBean
    private ArticleCommandService articleCommandService;

    @Override
    @BeforeEach
    public void setUp() throws Exception {
        super.setUp();
        RestAssuredMockMvc.mockMvc(mvc);
    }

    @Test
    public void should_read_article_success() throws Exception {
        String slug = "test-new-article";
        DateTime time = new DateTime();
        Article article = new Article("Test New Article", "Desc", "Body", Arrays.asList("java", "spring", "jpg"),
                user.getId(), time);
        ArticleData articleData = TestHelper.getArticleDataFromArticleAndUser(article, user);

        when(articleQueryService.findBySlug(eq(slug), eq(null))).thenReturn(Optional.of(articleData));

        RestAssuredMockMvc.when().get("/articles/{slug}", slug).then().statusCode(200)
                .body("article.slug", equalTo(slug)).body("article.body", equalTo(articleData.getBody()))
                .body("article.createdAt", equalTo(ISODateTimeFormat.dateTime().withZoneUTC().print(time)));
    }

    @Test
    public void should_404_if_article_not_found() throws Exception {
        when(articleQueryService.findBySlug(anyString(), any())).thenReturn(Optional.empty());
        RestAssuredMockMvc.when().get("/articles/not-exists").then().statusCode(404);
    }

    @Test
    public void should_update_article_content_success() throws Exception {
        String title = "new-title";
        String body = "new body";
        String description = "new description";
        Map<String, Object> updateParam = prepareUpdateParam(title, body, description);

        Article article = new Article(title, description, body, Arrays.asList("java", "spring", "jpg"), user.getId());

        ArticleData articleData = TestHelper.getArticleDataFromArticleAndUser(article, user);

        when(articleRepository.findBySlug(eq(article.getSlug()))).thenReturn(Optional.of(article));
        when(articleQueryService.findBySlug(eq(article.getSlug()), eq(user))).thenReturn(Optional.of(articleData));

        given().contentType("application/json").header("Authorization", "Token " + token).body(updateParam).when()
                .put("/articles/{slug}", article.getSlug()).then().statusCode(200)
                .body("article.slug", equalTo(articleData.getSlug()));
    }

    @Test
    public void should_get_403_if_not_author_to_update_article() throws Exception {
        String title = "new-title";
        String body = "new body";
        String description = "new description";
        Map<String, Object> updateParam = prepareUpdateParam(title, body, description);

        User anotherUser = new User("test", "123123", "0911", "test@test.com");

        Article article = new Article(title, description, body, Arrays.asList("java", "spring", "jpg"),
                anotherUser.getId());

        DateTime time = new DateTime();
        ArticleData articleData = new ArticleData(article.getId(), article.getSlug(), article.getTitle(),
                article.getDescription(), article.getBody(), false, 0, time, time, Arrays.asList("joda"),
                new ProfileData(anotherUser.getId(), anotherUser.getUsername(), false));

        when(articleRepository.findBySlug(eq(article.getSlug()))).thenReturn(Optional.of(article));
        when(articleQueryService.findBySlug(eq(article.getSlug()), eq(user))).thenReturn(Optional.of(articleData));

        given().contentType("application/json").header("Authorization", "Token " + token).body(updateParam).when()
                .put("/articles/{slug}", article.getSlug()).then().statusCode(403);
    }

    @Test
    public void should_delete_article_success() throws Exception {
        String title = "title";
        String body = "body";
        String description = "description";

        Article article = new Article(title, description, body, Arrays.asList("java", "spring", "jpg"), user.getId());
        when(articleRepository.findBySlug(eq(article.getSlug()))).thenReturn(Optional.of(article));

        given().header("Authorization", "Token " + token).when().delete("/articles/{slug}", article.getSlug()).then()
                .statusCode(204);

        verify(articleRepository).remove(eq(article));
    }

    @Test
    public void should_403_if_not_author_delete_article() throws Exception {
        String title = "new-title";
        String body = "new body";
        String description = "new description";

        User anotherUser = new User("test", "123123", "0911", "test@test.com");

        Article article = new Article(title, description, body, Arrays.asList("java", "spring", "jpg"),
                anotherUser.getId());

        when(articleRepository.findBySlug(eq(article.getSlug()))).thenReturn(Optional.of(article));
        given().header("Authorization", "Token " + token).when().delete("/articles/{slug}", article.getSlug()).then()
                .statusCode(403);
    }

    private HashMap<String, Object> prepareUpdateParam(final String title, final String body,
            final String description) {
        return new HashMap<String, Object>() {
            {
                put("article", new HashMap<String, Object>() {
                    {
                        put("title", title);
                        put("body", body);
                        put("description", description);
                    }
                });
            }
        };
    }

}
