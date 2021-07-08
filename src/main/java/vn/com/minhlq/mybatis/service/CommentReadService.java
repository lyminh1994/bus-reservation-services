package vn.com.minhlq.mybatis.service;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.joda.time.DateTime;

import vn.com.minhlq.common.CursorPageParameter;
import vn.com.minhlq.dto.CommentData;

@Mapper
public interface CommentReadService {

    CommentData findById(@Param("id") Long id);

    List<CommentData> findByArticleId(@Param("articleId") Long articleId);

    List<CommentData> findByArticleIdWithCursor(@Param("articleId") Long articleId,
            @Param("page") CursorPageParameter<DateTime> page);

}
