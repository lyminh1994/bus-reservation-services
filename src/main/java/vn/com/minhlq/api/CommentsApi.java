package vn.com.minhlq.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import vn.com.minhlq.dto.CommentData;
import vn.com.minhlq.dto.NewCommentParam;
import vn.com.minhlq.exception.NoAuthorizationException;
import vn.com.minhlq.exception.ResourceNotFoundException;
import vn.com.minhlq.model.Article;
import vn.com.minhlq.model.Comment;
import vn.com.minhlq.model.User;
import vn.com.minhlq.repository.ArticleRepository;
import vn.com.minhlq.repository.CommentRepository;
import vn.com.minhlq.service.CommentQueryService;
import vn.com.minhlq.utils.AuthorizationUtils;

@Tag(name = "Article Comments")
@RestController
@RequiredArgsConstructor
@RequestMapping("/articles/{slug}/comments")
public class CommentsApi {

    private ArticleRepository articleRepository;

    private CommentRepository commentRepository;

    private CommentQueryService commentQueryService;

    @PostMapping
    public ResponseEntity<?> createComment(@PathVariable("slug") String slug, @AuthenticationPrincipal User user,
            @Valid @RequestBody NewCommentParam newCommentParam) {
        Article article = articleRepository.findBySlug(slug).orElseThrow(ResourceNotFoundException::new);
        Comment comment = new Comment(newCommentParam.getBody(), user.getId(), article.getId());
        commentRepository.save(comment);
        return ResponseEntity.status(201)
                .body(commentResponse(commentQueryService.findById(comment.getId(), user).get()));
    }

    @GetMapping
    public ResponseEntity<?> getComments(@PathVariable("slug") String slug, @AuthenticationPrincipal User user) {
        Article article = articleRepository.findBySlug(slug).orElseThrow(ResourceNotFoundException::new);
        List<CommentData> comments = commentQueryService.findByArticleId(article.getId(), user);
        return ResponseEntity.ok(new HashMap<String, Object>() {
            {
                put("comments", comments);
            }
        });
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable("slug") String slug, @PathVariable("id") Long commentId,
            @AuthenticationPrincipal User user) {
        Article article = articleRepository.findBySlug(slug).orElseThrow(ResourceNotFoundException::new);
        return commentRepository.findById(article.getId(), commentId).map(comment -> {
            if (!AuthorizationUtils.canWriteComment(user, article, comment)) {
                throw new NoAuthorizationException();
            }
            commentRepository.remove(comment);
            return ResponseEntity.noContent().build();
        }).orElseThrow(ResourceNotFoundException::new);
    }

    private Map<String, Object> commentResponse(CommentData commentData) {
        return new HashMap<String, Object>() {
            {
                put("comment", commentData);
            }
        };
    }

}
