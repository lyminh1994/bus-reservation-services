package vn.com.minhlq.mybatis.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import vn.com.minhlq.model.Article;
import vn.com.minhlq.model.Tag;

@Mapper
public interface ArticleMapper {

    void insert(@Param("article") Article article);

    Article findById(@Param("id") Long id);

    Tag findTag(@Param("tagName") String tagName);

    void insertTag(@Param("tag") Tag tag);

    void insertArticleTagRelation(@Param("articleId") Long articleId, @Param("tagId") Long tagId);

    Article findBySlug(@Param("slug") String slug);

    void update(@Param("article") Article article);

    void delete(@Param("id") Long id);

}
