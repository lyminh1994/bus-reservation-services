package vn.com.minhlq.service.impl;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import vn.com.minhlq.common.CursorPageParameter;
import vn.com.minhlq.common.CursorPager;
import vn.com.minhlq.common.Page;
import vn.com.minhlq.dto.ArticleData;
import vn.com.minhlq.dto.ArticleDataList;
import vn.com.minhlq.dto.ArticleFavoriteCount;
import vn.com.minhlq.model.User;
import vn.com.minhlq.mybatis.service.ArticleFavoritesReadService;
import vn.com.minhlq.mybatis.service.ArticleReadService;
import vn.com.minhlq.mybatis.service.UserRelationshipQueryService;
import vn.com.minhlq.service.ArticleQueryService;

@Service
@AllArgsConstructor
public class ArticleQueryServiceImpl implements ArticleQueryService {

    private ArticleReadService articleReadService;

    private UserRelationshipQueryService userRelationshipQueryService;

    private ArticleFavoritesReadService articleFavoritesReadService;

    @Override
    public Optional<ArticleData> findById(Long id, User user) {
        ArticleData articleData = articleReadService.findById(id);
        if (articleData == null) {
            return Optional.empty();
        } else {
            if (user != null) {
                fillExtraInfo(id, user, articleData);
            }
            return Optional.of(articleData);
        }
    }

    @Override
    public Optional<ArticleData> findBySlug(String slug, User user) {
        ArticleData articleData = articleReadService.findBySlug(slug);
        if (articleData == null) {
            return Optional.empty();
        } else {
            if (user != null) {
                fillExtraInfo(articleData.getId(), user, articleData);
            }
            return Optional.of(articleData);
        }
    }

    @Override
    public CursorPager<ArticleData> findRecentArticlesWithCursor(String tag, String author, Long favoritedBy,
            CursorPageParameter<DateTime> page, User currentUser) {
        List<Long> articleIds = articleReadService.findArticlesWithCursor(tag, author, favoritedBy, page);
        if (articleIds.size() == 0) {
            return new CursorPager<>(new ArrayList<>(), page.getDirection(), false);
        } else {
            boolean hasExtra = articleIds.size() > page.getLimit();
            if (hasExtra) {
                articleIds.remove(page.getLimit());
            }
            if (!page.isNext()) {
                Collections.reverse(articleIds);
            }

            List<ArticleData> articles = articleReadService.findArticles(articleIds);
            fillExtraInfo(articles, currentUser);

            return new CursorPager<>(articles, page.getDirection(), hasExtra);
        }
    }

    @Override
    public CursorPager<ArticleData> findUserFeedWithCursor(User user, CursorPageParameter<DateTime> page) {
        List<Long> followdUsers = userRelationshipQueryService.followedUsers(user.getId());
        if (followdUsers.size() == 0) {
            return new CursorPager<>(new ArrayList<>(), page.getDirection(), false);
        } else {
            List<ArticleData> articles = articleReadService.findArticlesOfAuthorsWithCursor(followdUsers, page);
            boolean hasExtra = articles.size() > page.getLimit();
            if (hasExtra) {
                articles.remove(page.getLimit());
            }
            if (!page.isNext()) {
                Collections.reverse(articles);
            }
            fillExtraInfo(articles, user);
            return new CursorPager<>(articles, page.getDirection(), hasExtra);
        }
    }

    @Override
    public ArticleDataList findRecentArticles(String tag, String author, Long favoritedBy, Page page,
            User currentUser) {
        List<Long> articleIds = articleReadService.queryArticles(tag, author, favoritedBy, page);
        int articleCount = articleReadService.countArticle(tag, author, favoritedBy);
        if (articleIds.size() == 0) {
            return new ArticleDataList(new ArrayList<>(), articleCount);
        } else {
            List<ArticleData> articles = articleReadService.findArticles(articleIds);
            fillExtraInfo(articles, currentUser);
            return new ArticleDataList(articles, articleCount);
        }
    }

    @Override
    public ArticleDataList findUserFeed(User user, Page page) {
        List<Long> followdUsers = userRelationshipQueryService.followedUsers(user.getId());
        if (followdUsers.size() == 0) {
            return new ArticleDataList(new ArrayList<>(), 0);
        } else {
            List<ArticleData> articles = articleReadService.findArticlesOfAuthors(followdUsers, page);
            fillExtraInfo(articles, user);
            int count = articleReadService.countFeedSize(followdUsers);
            return new ArticleDataList(articles, count);
        }
    }

    private void fillExtraInfo(List<ArticleData> articles, User currentUser) {
        setFavoriteCount(articles);
        if (currentUser != null) {
            setIsFavorite(articles, currentUser);
            setIsFollowingAuthor(articles, currentUser);
        }
    }

    private void setIsFollowingAuthor(List<ArticleData> articles, User currentUser) {
        Set<Long> followingAuthors = userRelationshipQueryService.followingAuthors(currentUser.getId(),
                articles.stream().map(articleData1 -> articleData1.getProfileData().getId()).collect(toList()));
        articles.forEach(articleData -> {
            if (followingAuthors.contains(articleData.getProfileData().getId())) {
                articleData.getProfileData().setFollowing(true);
            }
        });
    }

    private void setFavoriteCount(List<ArticleData> articles) {
        List<ArticleFavoriteCount> favoritesCounts = articleFavoritesReadService
                .articlesFavoriteCount(articles.stream().map(ArticleData::getId).collect(toList()));
        Map<Long, Integer> countMap = new HashMap<>();
        favoritesCounts.forEach(item -> {
            countMap.put(item.getId(), item.getCount());
        });
        articles.forEach(articleData -> articleData.setFavoritesCount(countMap.get(articleData.getId())));
    }

    private void setIsFavorite(List<ArticleData> articles, User currentUser) {
        Set<Long> favoritedArticles = articleFavoritesReadService.userFavorites(
                articles.stream().map(articleData -> articleData.getId()).collect(toList()), currentUser);

        articles.forEach(articleData -> {
            if (favoritedArticles.contains(articleData.getId())) {
                articleData.setFavorited(true);
            }
        });
    }

    private void fillExtraInfo(Long id, User user, ArticleData articleData) {
        articleData.setFavorited(articleFavoritesReadService.isUserFavorite(user.getId(), id));
        articleData.setFavoritesCount(articleFavoritesReadService.articleFavoriteCount(id));
        articleData.getProfileData().setFollowing(
                userRelationshipQueryService.isUserFollowing(user.getId(), articleData.getProfileData().getId()));
    }
}
