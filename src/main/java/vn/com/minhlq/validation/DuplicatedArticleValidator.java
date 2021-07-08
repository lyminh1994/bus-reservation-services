package vn.com.minhlq.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import lombok.AllArgsConstructor;
import vn.com.minhlq.model.Article;
import vn.com.minhlq.service.ArticleQueryService;

@AllArgsConstructor
public class DuplicatedArticleValidator implements ConstraintValidator<DuplicatedArticleConstraint, String> {

    private ArticleQueryService articleQueryService;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return !articleQueryService.findBySlug(Article.toSlug(value), null).isPresent();
    }
}
