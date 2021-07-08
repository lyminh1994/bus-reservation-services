package vn.com.minhlq.service.impl;

import javax.validation.Valid;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import lombok.AllArgsConstructor;
import vn.com.minhlq.dto.NewArticleParam;
import vn.com.minhlq.dto.UpdateArticleParam;
import vn.com.minhlq.model.Article;
import vn.com.minhlq.model.User;
import vn.com.minhlq.repository.ArticleRepository;
import vn.com.minhlq.service.ArticleCommandService;

@Service
@Validated
@AllArgsConstructor
public class ArticleCommandServiceImpl implements ArticleCommandService {

    private ArticleRepository articleRepository;

    public Article createArticle(@Valid NewArticleParam newArticleParam, User creator) {
        Article article = new Article(newArticleParam.getTitle(), newArticleParam.getDescription(),
                newArticleParam.getBody(), newArticleParam.getTagList(), creator.getId());
        articleRepository.save(article);
        return article;
    }

    public Article updateArticle(Article article, @Valid UpdateArticleParam updateArticleParam) {
        article.update(updateArticleParam.getTitle(), updateArticleParam.getDescription(),
                updateArticleParam.getBody());
        articleRepository.save(article);
        return article;
    }

}
