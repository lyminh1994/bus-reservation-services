package vn.com.minhlq.api;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static java.util.Arrays.asList;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.joda.time.DateTime;
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
import vn.com.minhlq.security.SecurityConfig;
import vn.com.minhlq.service.ArticleCommandService;
import vn.com.minhlq.service.ArticleQueryService;

@WebMvcTest({ ArticlesApi.class })
@Import({ SecurityConfig.class, JacksonConfig.class })
public class ArticlesApiTest extends TestWithCurrentUser {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ArticleQueryService articleQueryService;

    @MockBean
    private ArticleCommandService articleCommandService;

    @Override
    @BeforeEach
    public void setUp() throws Exception {
        super.setUp();
        RestAssuredMockMvc.mockMvc(mvc);
    }

    @Test
    public void should_create_article_success() throws Exception {
        String title = "How to train your dragon";
        String slug = "how-to-train-your-dragon";
        String description = "Ever wonder how?";
        String body = "You have to believe";
        List<String> tagList = asList("reactjs", "angularjs", "dragons");
        Map<String, Object> param = prepareParam(title, description, body, tagList);

        ArticleData articleData = new ArticleData(123L, slug, title, description, body, false, 0, new DateTime(),
                new DateTime(), tagList, new ProfileData(1L, user.getUsername(), false));

        when(articleCommandService.createArticle(any(), any()))
                .thenReturn(new Article(title, description, body, tagList, user.getId()));

        when(articleQueryService.findBySlug(eq(Article.toSlug(title)), any())).thenReturn(Optional.empty());

        when(articleQueryService.findById(any(), any())).thenReturn(Optional.of(articleData));

        given().contentType("application/json").header("Authorization", "Token " + token).body(param).when()
                .post("/articles").then().statusCode(200).body("article.title", equalTo(title))
                .body("article.favorited", equalTo(false)).body("article.body", equalTo(body))
                .body("article.favoritesCount", equalTo(0)).body("article.author.username", equalTo(user.getUsername()))
                .body("article.author.id", equalTo(null));

        verify(articleCommandService).createArticle(any(), any());
    }

    @Test
    public void should_get_error_message_with_wrong_parameter() throws Exception {
        String title = "How to train your dragon";
        String description = "Ever wonder how?";
        String body = "";
        String[] tagList = { "reactjs", "angularjs", "dragons" };
        Map<String, Object> param = prepareParam(title, description, body, asList(tagList));

        given().contentType("application/json").header("Authorization", "Token " + token).body(param).when()
                .post("/articles").prettyPeek().then().statusCode(422)
                .body("errors.body[0]", equalTo("can't be empty"));
    }

    @Test
    public void should_get_error_message_with_duplicated_title() {
        String title = "How to train your dragon";
        String slug = "how-to-train-your-dragon";
        String description = "Ever wonder how?";
        String body = "You have to believe";
        String[] tagList = { "reactjs", "angularjs", "dragons" };
        Map<String, Object> param = prepareParam(title, description, body, asList(tagList));

        ArticleData articleData = new ArticleData(123L, slug, title, description, body, false, 0, new DateTime(),
                new DateTime(), asList(tagList), new ProfileData(1L, user.getUsername(), false));

        when(articleQueryService.findBySlug(eq(Article.toSlug(title)), any())).thenReturn(Optional.of(articleData));

        when(articleQueryService.findById(any(), any())).thenReturn(Optional.of(articleData));

        given().contentType("application/json").header("Authorization", "Token " + token).body(param).when()
                .post("/articles").prettyPeek().then().statusCode(422);
    }

    private HashMap<String, Object> prepareParam(final String title, final String description, final String body,
            final List<String> tagList) {
        return new HashMap<String, Object>() {
            {
                put("article", new HashMap<String, Object>() {
                    {
                        put("title", title);
                        put("description", description);
                        put("body", body);
                        put("tagList", tagList);
                    }
                });
            }
        };
    }

}
