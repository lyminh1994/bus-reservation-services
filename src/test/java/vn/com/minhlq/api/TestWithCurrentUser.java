package vn.com.minhlq.api;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import vn.com.minhlq.model.User;
import vn.com.minhlq.repository.FollowRepository;
import vn.com.minhlq.repository.UserRepository;
import vn.com.minhlq.security.jwt.JwtUtils;

@ExtendWith(SpringExtension.class)
abstract class TestWithCurrentUser {

    @MockBean
    protected UserRepository userRepository;

    @MockBean
    protected FollowRepository followRepository;

    protected User user;

    protected String token;

    protected String email;

    protected String username;

    @MockBean
    protected JwtUtils jwtService;

    protected void userFixture() {
        email = "john@jacob.com";
        username = "johnjacob";

        user = new User(username, "123", "099111", email);
        when(userRepository.findByUsername(eq(username))).thenReturn(Optional.of(user));
        when(userRepository.findById(eq(user.getId()))).thenReturn(Optional.of(user));

        token = "token";
        when(Optional.of(jwtService.getUsernameFromJWT(eq(token)))).thenReturn(Optional.of(user.getUsername()));
    }

    @BeforeAll
    public void setUp() throws Exception {
        userFixture();
    }

}
