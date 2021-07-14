package vn.com.minhlq.api;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static java.util.Arrays.asList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import vn.com.minhlq.TestHelper;
import vn.com.minhlq.common.Page;
import vn.com.minhlq.config.JacksonConfig;
import vn.com.minhlq.dto.ArticleDataList;
import vn.com.minhlq.repository.ArticleRepository;
import vn.com.minhlq.security.SecurityConfig;
import vn.com.minhlq.service.ArticleCommandService;
import vn.com.minhlq.service.ArticleQueryService;

@WebMvcTest(ArticlesApi.class)
@Import({ SecurityConfig.class, JacksonConfig.class })
public class ListArticleApiTest extends TestWithCurrentUser {

    @MockBean
    private ArticleRepository articleRepository;

    @MockBean
    private ArticleQueryService articleQueryService;

    @MockBean
    private ArticleCommandService articleCommandService;

    @Autowired
    private MockMvc mvc;

    @Override
    @BeforeAll
    public void setUp() throws Exception {
        super.setUp();
        RestAssuredMockMvc.mockMvc(mvc);
    }

    @Test
    public void should_get_default_article_list() throws Exception {
        ArticleDataList articleDataList = new ArticleDataList(
                asList(TestHelper.articleDataFixture(1L, user), TestHelper.articleDataFixture(2L, user)), 2);
        when(articleQueryService.findRecentArticles(eq(null), eq(null), eq(null), eq(new Page(0, 20)), eq(null)))
                .thenReturn(articleDataList);
        RestAssuredMockMvc.when().get("/articles").prettyPeek().then().statusCode(200);
    }

    @Test
    public void should_get_feeds_401_without_login() throws Exception {
        RestAssuredMockMvc.when().get("/articles/feed").prettyPeek().then().statusCode(401);
    }

    @Test
    public void should_get_feeds_success() throws Exception {
        ArticleDataList articleDataList = new ArticleDataList(
                asList(TestHelper.articleDataFixture(1L, user), TestHelper.articleDataFixture(2L, user)), 2);
        when(articleQueryService.findUserFeed(eq(user), eq(new Page(0, 20)))).thenReturn(articleDataList);

        given().header("Authorization", "Token " + token).when().get("/articles/feed").prettyPeek().then()
                .statusCode(200);
    }

}
