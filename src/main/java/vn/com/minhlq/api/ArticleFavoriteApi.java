package vn.com.minhlq.api;

import java.util.HashMap;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import vn.com.minhlq.dto.ArticleData;
import vn.com.minhlq.exception.ResourceNotFoundException;
import vn.com.minhlq.model.Article;
import vn.com.minhlq.model.ArticleFavorite;
import vn.com.minhlq.model.User;
import vn.com.minhlq.repository.ArticleFavoriteRepository;
import vn.com.minhlq.repository.ArticleRepository;
import vn.com.minhlq.service.ArticleQueryService;

@Tag(name = "Article Favorite")
@RestController
@RequiredArgsConstructor
@RequestMapping("/articles/{slug}/favorite")
public class ArticleFavoriteApi {

    private ArticleFavoriteRepository articleFavoriteRepository;

    private ArticleRepository articleRepository;

    private ArticleQueryService articleQueryService;

    @PostMapping
    public ResponseEntity<?> favoriteArticle(@PathVariable("slug") String slug, @AuthenticationPrincipal User user) {
        Article article = articleRepository.findBySlug(slug).orElseThrow(ResourceNotFoundException::new);
        ArticleFavorite articleFavorite = new ArticleFavorite(article.getId(), user.getId());
        articleFavoriteRepository.save(articleFavorite);
        return responseArticleData(articleQueryService.findBySlug(slug, user).get());
    }

    @DeleteMapping
    public ResponseEntity<?> unfavoriteArticle(@PathVariable("slug") String slug, @AuthenticationPrincipal User user) {
        Article article = articleRepository.findBySlug(slug).orElseThrow(ResourceNotFoundException::new);
        articleFavoriteRepository.find(article.getId(), user.getId()).ifPresent(favorite -> {
            articleFavoriteRepository.remove(favorite);
        });
        return responseArticleData(articleQueryService.findBySlug(slug, user).get());
    }

    private ResponseEntity<HashMap<String, Object>> responseArticleData(final ArticleData articleData) {
        return ResponseEntity.ok(new HashMap<String, Object>() {
            {
                put("article", articleData);
            }
        });
    }

}
