package vn.com.minhlq.service;

import vn.com.minhlq.dto.NewArticleParam;
import vn.com.minhlq.dto.UpdateArticleParam;
import vn.com.minhlq.model.Article;
import vn.com.minhlq.model.User;

public interface ArticleCommandService {

    Article createArticle(NewArticleParam newArticleParam, User creator);

    Article updateArticle(Article article, UpdateArticleParam updateArticleParam);

}
