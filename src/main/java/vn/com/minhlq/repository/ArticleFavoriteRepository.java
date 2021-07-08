package vn.com.minhlq.repository;

import java.util.Optional;

import vn.com.minhlq.model.ArticleFavorite;

public interface ArticleFavoriteRepository {

    void save(ArticleFavorite articleFavorite);

    Optional<ArticleFavorite> find(Long articleId, Long userId);

    void remove(ArticleFavorite favorite);

}
