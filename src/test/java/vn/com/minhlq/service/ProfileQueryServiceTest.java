package vn.com.minhlq.service;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import vn.com.minhlq.DbTestBase;
import vn.com.minhlq.dto.ProfileData;
import vn.com.minhlq.model.User;
import vn.com.minhlq.repository.UserRepository;

public class ProfileQueryServiceTest extends DbTestBase {

    @Autowired
    private ProfileQueryService profileQueryService;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void should_fetch_profile_success() {
        User currentUser = new User("a", "123", "09111", "a@test.com");
        User profileUser = new User("p", "123", "09111", "p@test.com");
        userRepository.save(profileUser);

        Optional<ProfileData> optional = profileQueryService.findByUsername(profileUser.getUsername(), currentUser);
        assertTrue(optional.isPresent());
    }

}
