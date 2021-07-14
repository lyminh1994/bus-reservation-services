package vn.com.minhlq.mybatis.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

import vn.com.minhlq.DbTestBase;
import vn.com.minhlq.model.Follow;
import vn.com.minhlq.model.User;
import vn.com.minhlq.repository.FollowRepository;
import vn.com.minhlq.repository.UserRepository;

@Import(MyBatisFollowRepository.class)
public class MyBatisFollowRepositoryTest extends DbTestBase {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FollowRepository followRepository;

    private User user;

    @BeforeAll
    public void setUp() {
        user = new User("aisensiy", "123", "0919991", "aisensiy@163.com");
    }

    @Test
    public void should_save_and_fetch_user_success() {
        userRepository.save(user);
        Optional<User> userOptional = userRepository.findByUsername("aisensiy");
        assertEquals(userOptional.get(), user);
        Optional<User> userOptional2 = userRepository.findByPhone("0919991");
        assertEquals(userOptional2.get(), user);
        Optional<User> userOptional3 = userRepository.findByEmail("aisensiy@163.com");
        assertEquals(userOptional3.get(), user);
    }

    @Test
    public void should_update_user_success() {
        String newEmail = "newemail@email.com";
        user.update("", "", "", newEmail);
        userRepository.save(user);
        Optional<User> optional = userRepository.findByEmail(user.getUsername());
        assertTrue(optional.isPresent());
        assertEquals(optional.get().getEmail(), newEmail);

        String newUsername = "newUsername";
        user.update(newUsername, "", "", "");
        userRepository.save(user);
        optional = userRepository.findByUsername(user.getEmail());
        assertTrue(optional.isPresent());
        assertEquals(optional.get().getUsername(), newUsername);
    }

    @Test
    public void should_create_new_user_follow_success() {
        User other = new User("other", "123", "0919991", "other@example.com");
        userRepository.save(other);

        Follow follow = new Follow(user.getId(), other.getId());
        followRepository.saveRelation(follow);
        assertTrue(followRepository.findRelation(user.getId(), other.getId()).isPresent());
    }

    @Test
    public void should_unfollow_user_success() {
        User other = new User("other", "123", "0911111", "other@example.com");
        userRepository.save(other);

        Follow follow = new Follow(user.getId(), other.getId());
        followRepository.saveRelation(follow);

        followRepository.removeRelation(follow);
        assertFalse(followRepository.findRelation(user.getId(), other.getId()).isPresent());
    }

}
