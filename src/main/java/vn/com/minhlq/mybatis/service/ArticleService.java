package vn.com.minhlq.mybatis.service;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import vn.com.minhlq.common.CursorPageParameter;
import vn.com.minhlq.common.Page;
import vn.com.minhlq.dto.ArticleData;

@Mapper
public interface ArticleService {

    ArticleData findById(@Param("id") Long id);

    ArticleData findBySlug(@Param("slug") String slug);

    List<Long> queryArticles(@Param("tag") String tag, @Param("author") String author,
            @Param("favoritedBy") Long favoritedBy, @Param("page") Page page);

    int countArticle(@Param("tag") String tag, @Param("author") String author, @Param("favoritedBy") Long favoritedBy);

    List<ArticleData> findArticles(@Param("articleIds") List<Long> articleIds);

    List<ArticleData> findArticlesOfAuthors(@Param("authors") List<Long> authors, @Param("page") Page page);

    List<ArticleData> findArticlesOfAuthorsWithCursor(@Param("authors") List<Long> authors,
            @Param("page") CursorPageParameter<?> page);

    int countFeedSize(@Param("authors") List<Long> authors);

    List<Long> findArticlesWithCursor(@Param("tag") String tag, @Param("author") String author,
            @Param("favoritedBy") Long favoritedBy, @Param("page") CursorPageParameter<?> page);

}
