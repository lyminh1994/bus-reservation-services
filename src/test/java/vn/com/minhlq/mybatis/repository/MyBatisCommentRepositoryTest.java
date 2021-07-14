package vn.com.minhlq.mybatis.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import vn.com.minhlq.DbTestBase;
import vn.com.minhlq.model.Comment;
import vn.com.minhlq.repository.CommentRepository;

public class MyBatisCommentRepositoryTest extends DbTestBase {

    @Autowired
    private CommentRepository commentRepository;

    @Test
    public void should_create_and_fetch_comment_success() {
        Comment comment = new Comment("content", 123L, 456L);
        commentRepository.save(comment);

        Optional<Comment> optional = commentRepository.findById(456L, comment.getId());
        assertTrue(optional.isPresent());
        assertEquals(optional.get(), comment);
    }

}
