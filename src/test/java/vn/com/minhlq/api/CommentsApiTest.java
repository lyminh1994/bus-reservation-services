package vn.com.minhlq.api;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import vn.com.minhlq.config.JacksonConfig;
import vn.com.minhlq.dto.CommentData;
import vn.com.minhlq.dto.ProfileData;
import vn.com.minhlq.model.Article;
import vn.com.minhlq.model.Comment;
import vn.com.minhlq.model.User;
import vn.com.minhlq.repository.ArticleRepository;
import vn.com.minhlq.repository.CommentRepository;
import vn.com.minhlq.security.SecurityConfig;
import vn.com.minhlq.service.CommentQueryService;

@WebMvcTest(CommentsApi.class)
@Import({ SecurityConfig.class, JacksonConfig.class })
public class CommentsApiTest extends TestWithCurrentUser {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ArticleRepository articleRepository;

    @MockBean
    private CommentRepository commentRepository;

    @MockBean
    private CommentQueryService commentQueryService;

    private Article article;

    private CommentData commentData;

    private Comment comment;

    @BeforeEach
    public void setUp() throws Exception {
        RestAssuredMockMvc.mockMvc(mvc);
        super.setUp();
        article = new Article("title", "desc", "body", Arrays.asList("test", "java"), user.getId());
        when(articleRepository.findBySlug(eq(article.getSlug()))).thenReturn(Optional.of(article));
        comment = new Comment("comment", user.getId(), article.getId());
        commentData = new CommentData(comment.getId(), comment.getBody(), comment.getArticleId(),
                comment.getCreatedAt(), comment.getCreatedAt(),
                new ProfileData(user.getId(), user.getUsername(), false));
    }

    @Test
    public void should_create_comment_success() throws Exception {
        Map<String, Object> param = new HashMap<String, Object>() {
            {
                put("comment", new HashMap<String, Object>() {
                    {
                        put("body", "comment content");
                    }
                });
            }
        };

        when(commentQueryService.findById(anyLong(), eq(user))).thenReturn(Optional.of(commentData));

        given().contentType("application/json").header("Authorization", "Token " + token).body(param).when()
                .post("/articles/{slug}/comments", article.getSlug()).then().statusCode(201)
                .body("comment.body", equalTo(commentData.getBody()));
    }

    @Test
    public void should_get_422_with_empty_body() throws Exception {
        Map<String, Object> param = new HashMap<String, Object>() {
            {
                put("comment", new HashMap<String, Object>() {
                    {
                        put("body", "");
                    }
                });
            }
        };

        given().contentType("application/json").header("Authorization", "Token " + token).body(param).when()
                .post("/articles/{slug}/comments", article.getSlug()).then().statusCode(422)
                .body("errors.body[0]", equalTo("can't be empty"));
    }

    @Test
    public void should_get_comments_of_article_success() throws Exception {
        when(commentQueryService.findByArticleId(anyLong(), eq(null))).thenReturn(Arrays.asList(commentData));
        RestAssuredMockMvc.when().get("/articles/{slug}/comments", article.getSlug()).prettyPeek().then()
                .statusCode(200).body("comments[0].id", equalTo(commentData.getId()));
    }

    @Test
    public void should_delete_comment_success() throws Exception {
        when(commentRepository.findById(eq(article.getId()), eq(comment.getId()))).thenReturn(Optional.of(comment));

        given().header("Authorization", "Token " + token).when()
                .delete("/articles/{slug}/comments/{id}", article.getSlug(), comment.getId()).then().statusCode(204);
    }

    @Test
    public void should_get_403_if_not_author_of_article_or_author_of_comment_when_delete_comment() throws Exception {
        User anotherUser = new User("other", "123", "0911", "other@example.com");
        when(userRepository.findByUsername(eq(anotherUser.getUsername()))).thenReturn(Optional.of(anotherUser));
        when(Optional.of(jwtService.getUsernameFromJWT(any()))).thenReturn(Optional.of(anotherUser.getUsername()));
        when(userRepository.findById(eq(anotherUser.getId()))).thenReturn(Optional.ofNullable(anotherUser));

        when(commentRepository.findById(eq(article.getId()), eq(comment.getId()))).thenReturn(Optional.of(comment));
        String token = jwtService.createJWT(any(), false);
        when(userRepository.findById(eq(anotherUser.getId()))).thenReturn(Optional.of(anotherUser));
        given().header("Authorization", "Token " + token).when()
                .delete("/articles/{slug}/comments/{id}", article.getSlug(), comment.getId()).then().statusCode(403);
    }

}
