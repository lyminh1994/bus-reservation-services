package vn.com.minhlq.utils;

import lombok.experimental.UtilityClass;
import vn.com.minhlq.model.Article;
import vn.com.minhlq.model.Comment;
import vn.com.minhlq.model.User;

@UtilityClass
public class AuthorizationUtil {

    public boolean canWriteArticle(User user, Article article) {
        return user.getId().equals(article.getUserId());
    }

    public boolean canWriteComment(User user, Article article, Comment comment) {
        return user.getId().equals(article.getUserId()) || user.getId().equals(comment.getUserId());
    }

}
