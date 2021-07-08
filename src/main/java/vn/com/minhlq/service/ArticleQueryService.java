package vn.com.minhlq.service;

import java.util.Optional;

import org.joda.time.DateTime;

import vn.com.minhlq.common.CursorPageParameter;
import vn.com.minhlq.common.CursorPager;
import vn.com.minhlq.common.Page;
import vn.com.minhlq.dto.ArticleData;
import vn.com.minhlq.dto.ArticleDataList;
import vn.com.minhlq.model.User;

public interface ArticleQueryService {

    Optional<ArticleData> findById(Long id, User user);

    Optional<ArticleData> findBySlug(String slug, User user);

    CursorPager<ArticleData> findRecentArticlesWithCursor(String tag, String author, Long favoritedBy,
            CursorPageParameter<DateTime> page, User currentUser);

    CursorPager<ArticleData> findUserFeedWithCursor(User user, CursorPageParameter<DateTime> page);

    ArticleDataList findRecentArticles(String tag, String author, Long favoritedBy, Page page, User currentUser);

    ArticleDataList findUserFeed(User user, Page page);

}
