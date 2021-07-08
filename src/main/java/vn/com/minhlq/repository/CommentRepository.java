package vn.com.minhlq.repository;

import java.util.Optional;

import vn.com.minhlq.model.Comment;

public interface CommentRepository {

    void save(Comment comment);

    Optional<Comment> findById(Long articleId, Long id);

    void remove(Comment comment);

}
