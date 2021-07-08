package vn.com.minhlq.service;

import java.util.List;
import java.util.Optional;

import org.joda.time.DateTime;

import vn.com.minhlq.common.CursorPageParameter;
import vn.com.minhlq.common.CursorPager;
import vn.com.minhlq.dto.CommentData;
import vn.com.minhlq.model.User;

public interface CommentQueryService {

    Optional<CommentData> findById(Long id, User user);

    List<CommentData> findByArticleId(Long articleId, User user);

    CursorPager<CommentData> findByArticleIdWithCursor(Long articleId, User user, CursorPageParameter<DateTime> page);

}
