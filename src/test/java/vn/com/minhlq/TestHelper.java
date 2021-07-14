package vn.com.minhlq;

import java.util.ArrayList;
import java.util.Arrays;

import org.joda.time.DateTime;

import vn.com.minhlq.dto.ArticleData;
import vn.com.minhlq.dto.ProfileData;
import vn.com.minhlq.model.Article;
import vn.com.minhlq.model.User;

public class TestHelper {
    public static ArticleData articleDataFixture(Long id, User user) {
        DateTime now = new DateTime();
        return new ArticleData(id, "title-" + id, "title " + id, "desc " + id, "body " + id, false, 0, now, now,
                new ArrayList<>(), new ProfileData(user.getId(), user.getUsername(), false));
    }

    public static ArticleData getArticleDataFromArticleAndUser(Article article, User user) {
        return new ArticleData(article.getId(), article.getSlug(), article.getTitle(), article.getDescription(),
                article.getBody(), false, 0, article.getCreatedAt(), article.getUpdatedAt(), Arrays.asList("joda"),
                new ProfileData(user.getId(), user.getUsername(), false));
    }
}
