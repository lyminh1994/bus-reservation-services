package vn.com.minhlq.mybatis.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import vn.com.minhlq.model.ArticleFavorite;
import vn.com.minhlq.mybatis.mapper.ArticleFavoriteMapper;
import vn.com.minhlq.repository.ArticleFavoriteRepository;

@Repository
@RequiredArgsConstructor
public class MyBatisArticleFavoriteRepository implements ArticleFavoriteRepository {

    private ArticleFavoriteMapper mapper;

    @Override
    public void save(ArticleFavorite articleFavorite) {
        if (mapper.find(articleFavorite.getArticleId(), articleFavorite.getUserId()) == null) {
            mapper.insert(articleFavorite);
        }
    }

    @Override
    public Optional<ArticleFavorite> find(Long articleId, Long userId) {
        return Optional.ofNullable(mapper.find(articleId, userId));
    }

    @Override
    public void remove(ArticleFavorite favorite) {
        mapper.delete(favorite);
    }

}
