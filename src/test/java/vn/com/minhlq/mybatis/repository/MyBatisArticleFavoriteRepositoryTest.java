package vn.com.minhlq.mybatis.repository;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import vn.com.minhlq.DbTestBase;
import vn.com.minhlq.model.ArticleFavorite;
import vn.com.minhlq.mybatis.mapper.ArticleFavoriteMapper;
import vn.com.minhlq.repository.ArticleFavoriteRepository;

public class MyBatisArticleFavoriteRepositoryTest extends DbTestBase {

    @Autowired
    private ArticleFavoriteRepository articleFavoriteRepository;

    @Autowired
    private ArticleFavoriteMapper articleFavoriteMapper;

    @Test
    public void should_save_and_fetch_articleFavorite_success() {
        ArticleFavorite articleFavorite = new ArticleFavorite(123L, 456L);
        articleFavoriteRepository.save(articleFavorite);
        assertNotNull(articleFavoriteMapper.find(articleFavorite.getArticleId(), articleFavorite.getUserId()));
    }

    @Test
    public void should_remove_favorite_success() {
        ArticleFavorite articleFavorite = new ArticleFavorite(123L, 456L);
        articleFavoriteRepository.save(articleFavorite);
        articleFavoriteRepository.remove(articleFavorite);
        assertFalse(articleFavoriteRepository.find(123L, 456L).isPresent());
    }

}
