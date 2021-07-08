package vn.com.minhlq.mybatis.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import vn.com.minhlq.model.Article;
import vn.com.minhlq.model.Tag;
import vn.com.minhlq.mybatis.mapper.ArticleMapper;
import vn.com.minhlq.repository.ArticleRepository;

@Repository
@AllArgsConstructor
public class MyBatisArticleRepository implements ArticleRepository {

    private ArticleMapper articleMapper;

    @Override
    @Transactional
    public void save(Article article) {
        if (articleMapper.findById(article.getId()) == null) {
            createNew(article);
        } else {
            articleMapper.update(article);
        }
    }

    private void createNew(Article article) {
        for (Tag tag : article.getTags()) {
            Tag targetTag = Optional.ofNullable(articleMapper.findTag(tag.getName())).orElseGet(() -> {
                articleMapper.insertTag(tag);
                return tag;
            });
            articleMapper.insertArticleTagRelation(article.getId(), targetTag.getId());
        }
        articleMapper.insert(article);
    }

    @Override
    public Optional<Article> findById(Long id) {
        return Optional.ofNullable(articleMapper.findById(id));
    }

    @Override
    public Optional<Article> findBySlug(String slug) {
        return Optional.ofNullable(articleMapper.findBySlug(slug));
    }

    @Override
    public void remove(Article article) {
        articleMapper.delete(article.getId());
    }

}
