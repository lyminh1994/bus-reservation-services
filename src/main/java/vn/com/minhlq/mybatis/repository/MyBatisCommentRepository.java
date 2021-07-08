package vn.com.minhlq.mybatis.repository;

import java.util.Optional;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import vn.com.minhlq.model.Comment;
import vn.com.minhlq.mybatis.mapper.CommentMapper;
import vn.com.minhlq.repository.CommentRepository;

@Component
@AllArgsConstructor
public class MyBatisCommentRepository implements CommentRepository {

    private CommentMapper commentMapper;

    @Override
    public void save(Comment comment) {
        commentMapper.insert(comment);
    }

    @Override
    public Optional<Comment> findById(Long articleId, Long id) {
        return Optional.ofNullable(commentMapper.findById(articleId, id));
    }

    @Override
    public void remove(Comment comment) {
        commentMapper.delete(comment.getId());
    }

}
