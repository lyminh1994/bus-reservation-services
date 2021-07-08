package vn.com.minhlq.mybatis.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import vn.com.minhlq.model.ArticleFavorite;

@Mapper
public interface ArticleFavoriteMapper {

    ArticleFavorite find(@Param("articleId") Long articleId, @Param("userId") Long userId);

    void insert(@Param("articleFavorite") ArticleFavorite articleFavorite);

    void delete(@Param("favorite") ArticleFavorite favorite);

}
