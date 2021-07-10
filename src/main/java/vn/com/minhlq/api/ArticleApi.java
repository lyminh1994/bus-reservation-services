package vn.com.minhlq.api;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import vn.com.minhlq.dto.ArticleData;
import vn.com.minhlq.dto.UpdateArticleParam;
import vn.com.minhlq.exception.NoAuthorizationException;
import vn.com.minhlq.exception.ResourceNotFoundException;
import vn.com.minhlq.model.User;
import vn.com.minhlq.repository.ArticleRepository;
import vn.com.minhlq.service.ArticleCommandService;
import vn.com.minhlq.service.ArticleQueryService;
import vn.com.minhlq.utils.AuthorizationUtils;

@Tag(name = "Article")
@RestController
@RequiredArgsConstructor
@RequestMapping("/articles/{slug}")
public class ArticleApi {

    private ArticleQueryService articleQueryService;

    private ArticleRepository articleRepository;

    private ArticleCommandService articleCommandService;

    @GetMapping
    public ResponseEntity<?> article(@PathVariable("slug") String slug, @AuthenticationPrincipal User user) {
        return articleQueryService.findBySlug(slug, user)
                .map(articleData -> ResponseEntity.ok(articleResponse(articleData)))
                .orElseThrow(ResourceNotFoundException::new);
    }

    @PutMapping
    public ResponseEntity<?> updateArticle(@PathVariable("slug") String slug, @AuthenticationPrincipal User user,
            @Valid @RequestBody UpdateArticleParam updateArticleParam) {
        return articleRepository.findBySlug(slug).map(article -> {
            if (!AuthorizationUtils.canWriteArticle(user, article)) {
                throw new NoAuthorizationException();
            }
            articleCommandService.updateArticle(article, updateArticleParam);
            return ResponseEntity.ok(articleResponse(articleQueryService.findBySlug(slug, user).get()));
        }).orElseThrow(ResourceNotFoundException::new);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteArticle(@PathVariable("slug") String slug, @AuthenticationPrincipal User user) {
        return articleRepository.findBySlug(slug).map(article -> {
            if (!AuthorizationUtils.canWriteArticle(user, article)) {
                throw new NoAuthorizationException();
            }
            articleRepository.remove(article);
            return ResponseEntity.noContent().build();
        }).orElseThrow(ResourceNotFoundException::new);
    }

    private Map<String, Object> articleResponse(ArticleData articleData) {
        return new HashMap<String, Object>() {
            {
                put("article", articleData);
            }
        };
    }

}
