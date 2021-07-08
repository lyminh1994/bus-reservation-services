package vn.com.minhlq.mybatis.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import vn.com.minhlq.model.Comment;

@Mapper
public interface CommentMapper {

    void insert(@Param("comment") Comment comment);

    Comment findById(@Param("articleId") Long articleId, @Param("id") Long id);

    void delete(@Param("id") Long id);

}
