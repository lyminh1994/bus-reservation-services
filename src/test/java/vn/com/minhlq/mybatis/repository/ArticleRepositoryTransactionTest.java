package vn.com.minhlq.mybatis.repository;

import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import vn.com.minhlq.model.Article;
import vn.com.minhlq.model.User;
import vn.com.minhlq.mybatis.mapper.ArticleMapper;
import vn.com.minhlq.repository.ArticleRepository;
import vn.com.minhlq.repository.UserRepository;

@ActiveProfiles("dev")
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(SpringExtension.class)
public class ArticleRepositoryTransactionTest {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ArticleMapper articleMapper;

    @Test
    public void transactional_test() {
        User user = new User("aisensiy", "123", "0911", "aisensiy@gmail.com");
        userRepository.save(user);
        Article article = new Article("test", "desc", "body", Arrays.asList("java", "spring"), user.getId());
        articleRepository.save(article);
        Article anotherArticle = new Article("test", "desc", "body", Arrays.asList("java", "spring", "other"),
                user.getId());
        try {
            articleRepository.save(anotherArticle);
        } catch (Exception e) {
            assertNull(articleMapper.findTag("other"));
        }
    }

}
