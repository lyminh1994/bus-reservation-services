package vn.com.minhlq.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Optional;

import org.joda.time.DateTime;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import vn.com.minhlq.DbTestBase;
import vn.com.minhlq.common.CursorPageParameter;
import vn.com.minhlq.common.CursorPager;
import vn.com.minhlq.common.CursorPager.Direction;
import vn.com.minhlq.common.DateTimeCursor;
import vn.com.minhlq.common.Page;
import vn.com.minhlq.dto.ArticleData;
import vn.com.minhlq.dto.ArticleDataList;
import vn.com.minhlq.model.Article;
import vn.com.minhlq.model.ArticleFavorite;
import vn.com.minhlq.model.Follow;
import vn.com.minhlq.model.User;
import vn.com.minhlq.repository.ArticleFavoriteRepository;
import vn.com.minhlq.repository.ArticleRepository;
import vn.com.minhlq.repository.FollowRepository;
import vn.com.minhlq.repository.UserRepository;

public class ArticleQueryServiceTest extends DbTestBase {

    @Autowired
    private ArticleQueryService queryService;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FollowRepository followRepository;

    @Autowired
    private ArticleFavoriteRepository articleFavoriteRepository;

    private User user;

    private Article article;

    @BeforeAll
    public void setUp() {
        user = new User("aisensiy", "123", "0911", "aisensiy@gmail.com");
        userRepository.save(user);
        article = new Article("test", "desc", "body", Arrays.asList("java", "spring"), user.getId(), new DateTime());
        articleRepository.save(article);
    }

    @Test
    public void should_fetch_article_success() {
        Optional<ArticleData> optional = queryService.findById(article.getId(), user);
        assertTrue(optional.isPresent());

        ArticleData fetched = optional.get();
        assertEquals(fetched.getFavoritesCount(), 0);
        assertFalse(fetched.isFavorited());
        assertNotNull(fetched.getCreatedAt());
        assertNotNull(fetched.getUpdatedAt());
        assertTrue(fetched.getTagList().contains("java"));
    }

    @Test
    public void should_get_article_with_right_favorite_and_favorite_count() {
        User anotherUser = new User("other", "123", "0911", "other@test.com");
        userRepository.save(anotherUser);
        articleFavoriteRepository.save(new ArticleFavorite(article.getId(), anotherUser.getId()));

        Optional<ArticleData> optional = queryService.findById(article.getId(), anotherUser);
        assertTrue(optional.isPresent());

        ArticleData articleData = optional.get();
        assertEquals(articleData.getFavoritesCount(), 1);
        assertTrue(articleData.isFavorited());
    }

    @Test
    public void should_get_default_article_list() {
        Article anotherArticle = new Article("new article", "desc", "body", Arrays.asList("test"), user.getId(),
                new DateTime().minusHours(1));
        articleRepository.save(anotherArticle);

        ArticleDataList recentArticles = queryService.findRecentArticles(null, null, null, new Page(), user);
        assertEquals(recentArticles.getCount(), 2);
        assertEquals(recentArticles.getArticleDatas().size(), 2);
        assertEquals(recentArticles.getArticleDatas().get(0).getId(), article.getId());

        ArticleDataList noData = queryService.findRecentArticles(null, null, null, new Page(2, 10), user);
        assertEquals(noData.getCount(), 2);
        assertEquals(noData.getArticleDatas().size(), 0);
    }

    @Test
    public void should_get_default_article_list_by_cursor() {
        Article anotherArticle = new Article("new article", "desc", "body", Arrays.asList("test"), user.getId(),
                new DateTime().minusHours(1));
        articleRepository.save(anotherArticle);

        CursorPager<ArticleData> recentArticles = queryService.findRecentArticlesWithCursor(null, null, null,
                new CursorPageParameter<>(null, 20, Direction.NEXT), user);
        assertEquals(recentArticles.getData().size(), 2);
        assertEquals(recentArticles.getData().get(0).getId(), article.getId());

        CursorPager<ArticleData> noData = queryService
                .findRecentArticlesWithCursor(null, null, null,
                        new CursorPageParameter<DateTime>(
                                DateTimeCursor.parse(recentArticles.getEndCursor().toString()), 20, Direction.NEXT),
                        user);
        assertEquals(noData.getData().size(), 0);
        assertEquals(noData.getStartCursor(), null);

        CursorPager<ArticleData> prevArticles = queryService.findRecentArticlesWithCursor(null, null, null,
                new CursorPageParameter<>(null, 20, Direction.PREV), user);
        assertEquals(prevArticles.getData().size(), 2);
    }

    @Test
    public void should_query_article_by_author() {
        User anotherUser = new User("other", "123", "0911", "other@email.com");
        userRepository.save(anotherUser);

        Article anotherArticle = new Article("new article", "desc", "body", Arrays.asList("test"), anotherUser.getId());
        articleRepository.save(anotherArticle);

        ArticleDataList recentArticles = queryService.findRecentArticles(null, user.getUsername(), null, new Page(),
                user);
        assertEquals(recentArticles.getArticleDatas().size(), 1);
        assertEquals(recentArticles.getCount(), 1);
    }

    @Test
    public void should_query_article_by_favorite() {
        User anotherUser = new User("other", "123", "09111", "other@email.com");
        userRepository.save(anotherUser);

        Article anotherArticle = new Article("new article", "desc", "body", Arrays.asList("test"), anotherUser.getId());
        articleRepository.save(anotherArticle);

        ArticleFavorite articleFavorite = new ArticleFavorite(article.getId(), anotherUser.getId());
        articleFavoriteRepository.save(articleFavorite);

        ArticleDataList recentArticles = queryService.findRecentArticles(null, null, anotherUser.getId(), new Page(),
                anotherUser);
        assertEquals(recentArticles.getArticleDatas().size(), 1);
        assertEquals(recentArticles.getCount(), 1);
        ArticleData articleData = recentArticles.getArticleDatas().get(0);
        assertEquals(articleData.getId(), article.getId());
        assertEquals(articleData.getFavoritesCount(), 1);
        assertTrue(articleData.isFavorited());
    }

    @Test
    public void should_query_article_by_tag() {
        Article anotherArticle = new Article("new article", "desc", "body", Arrays.asList("test"), user.getId());
        articleRepository.save(anotherArticle);

        ArticleDataList recentArticles = queryService.findRecentArticles("spring", null, null, new Page(), user);
        assertEquals(recentArticles.getArticleDatas().size(), 1);
        assertEquals(recentArticles.getCount(), 1);
        assertEquals(recentArticles.getArticleDatas().get(0).getId(), article.getId());

        ArticleDataList noTag = queryService.findRecentArticles("noTag", null, null, new Page(), user);
        assertEquals(noTag.getCount(), 0);
    }

    @Test
    public void should_show_following_if_user_followed_author() {
        User anotherUser = new User("other", "123", "09111", "other@email.com");
        userRepository.save(anotherUser);

        Follow follow = new Follow(anotherUser.getId(), user.getId());
        followRepository.saveRelation(follow);

        ArticleDataList recentArticles = queryService.findRecentArticles(null, null, null, new Page(), anotherUser);
        assertEquals(recentArticles.getCount(), 1);
        ArticleData articleData = recentArticles.getArticleDatas().get(0);
        assertTrue(articleData.getProfileData().isFollowing());
    }

    @Test
    public void should_get_user_feed() {
        User anotherUser = new User("other", "123", "09111", "other@email.com");
        userRepository.save(anotherUser);

        Follow follow = new Follow(anotherUser.getId(), user.getId());
        followRepository.saveRelation(follow);

        ArticleDataList userFeed = queryService.findUserFeed(user, new Page());
        assertEquals(userFeed.getCount(), 0);

        ArticleDataList anotherUserFeed = queryService.findUserFeed(anotherUser, new Page());
        assertEquals(anotherUserFeed.getCount(), 1);
        ArticleData articleData = anotherUserFeed.getArticleDatas().get(0);
        assertTrue(articleData.getProfileData().isFollowing());
    }

}
