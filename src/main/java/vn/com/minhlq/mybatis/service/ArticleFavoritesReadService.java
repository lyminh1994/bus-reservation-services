package vn.com.minhlq.mybatis.service;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import vn.com.minhlq.dto.ArticleFavoriteCount;
import vn.com.minhlq.model.User;

@Mapper
public interface ArticleFavoritesReadService {

    boolean isUserFavorite(@Param("userId") Long userId, @Param("articleId") Long articleId);

    int articleFavoriteCount(@Param("articleId") Long articleId);

    List<ArticleFavoriteCount> articlesFavoriteCount(@Param("ids") List<Long> ids);

    Set<Long> userFavorites(@Param("ids") List<Long> ids, @Param("currentUser") User currentUser);

}
