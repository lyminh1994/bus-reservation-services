package vn.com.minhlq.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import vn.com.minhlq.DbTestBase;
import vn.com.minhlq.dto.CommentData;
import vn.com.minhlq.model.Article;
import vn.com.minhlq.model.Comment;
import vn.com.minhlq.model.Follow;
import vn.com.minhlq.model.User;
import vn.com.minhlq.repository.ArticleRepository;
import vn.com.minhlq.repository.CommentRepository;
import vn.com.minhlq.repository.FollowRepository;
import vn.com.minhlq.repository.UserRepository;

public class CommentQueryServiceTest extends DbTestBase {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FollowRepository followRepository;

    @Autowired
    private CommentQueryService commentQueryService;

    @Autowired
    private ArticleRepository articleRepository;

    private User user;

    @BeforeAll
    public void setUp() {
        user = new User("aisensiy", "123", "09111", "aisensiy@test.com");
        userRepository.save(user);
    }

    @Test
    public void should_read_comment_success() {
        Comment comment = new Comment("content", user.getId(), 123L);
        commentRepository.save(comment);

        Optional<CommentData> optional = commentQueryService.findById(comment.getId(), user);
        assertTrue(optional.isPresent());
        CommentData commentData = optional.get();
        assertEquals(commentData.getProfileData().getUsername(), user.getUsername());
    }

    @Test
    public void should_read_comments_of_article() {
        Article article = new Article("title", "desc", "body", Arrays.asList("java"), user.getId());
        articleRepository.save(article);

        User user2 = new User("user2", "123", "09111", "user2@email.com");
        userRepository.save(user2);
        followRepository.saveRelation(new Follow(user.getId(), user2.getId()));

        Comment comment1 = new Comment("content1", user.getId(), article.getId());
        commentRepository.save(comment1);
        Comment comment2 = new Comment("content2", user2.getId(), article.getId());
        commentRepository.save(comment2);

        List<CommentData> comments = commentQueryService.findByArticleId(article.getId(), user);
        assertEquals(comments.size(), 2);
    }

}
