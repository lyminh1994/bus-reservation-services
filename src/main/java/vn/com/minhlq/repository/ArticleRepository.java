package vn.com.minhlq.repository;

import java.util.Optional;

import vn.com.minhlq.model.Article;

public interface ArticleRepository {

    void save(Article article);

    Optional<Article> findById(Long id);

    Optional<Article> findBySlug(String slug);

    void remove(Article article);

}
