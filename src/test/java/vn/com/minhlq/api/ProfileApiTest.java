package vn.com.minhlq.api;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import vn.com.minhlq.config.JacksonConfig;
import vn.com.minhlq.dto.ProfileData;
import vn.com.minhlq.model.Follow;
import vn.com.minhlq.model.User;
import vn.com.minhlq.security.SecurityConfig;
import vn.com.minhlq.service.ProfileQueryService;

@WebMvcTest(ProfileApi.class)
@Import({ SecurityConfig.class, JacksonConfig.class })
public class ProfileApiTest extends TestWithCurrentUser {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ProfileQueryService profileQueryService;

    private User anotherUser;

    private ProfileData profileData;

    @BeforeAll
    public void setUp() throws Exception {
        super.setUp();
        RestAssuredMockMvc.mockMvc(mvc);
        anotherUser = new User("username", "123", "09111", "username@test.com");
        profileData = new ProfileData(anotherUser.getId(), anotherUser.getUsername(), false);
        when(userRepository.findByUsername(eq(anotherUser.getUsername()))).thenReturn(Optional.of(anotherUser));
    }

    @Test
    public void should_get_user_profile_success() throws Exception {
        when(profileQueryService.findByUsername(eq(profileData.getUsername()), eq(null)))
                .thenReturn(Optional.of(profileData));
        RestAssuredMockMvc.when().get("/profiles/{username}", profileData.getUsername()).prettyPeek().then()
                .statusCode(200).body("profile.username", equalTo(profileData.getUsername()));
    }

    @Test
    public void should_follow_user_success() throws Exception {
        when(profileQueryService.findByUsername(eq(profileData.getUsername()), eq(user)))
                .thenReturn(Optional.of(profileData));
        given().header("Authorization", "Token " + token).when()
                .post("/profiles/{username}/follow", anotherUser.getUsername()).prettyPeek().then().statusCode(200);
        verify(followRepository).saveRelation(new Follow(user.getId(), anotherUser.getId()));
    }

    @Test
    public void should_unfollow_user_success() throws Exception {
        Follow follow = new Follow(user.getId(), anotherUser.getId());
        when(followRepository.findRelation(eq(user.getId()), eq(anotherUser.getId()))).thenReturn(Optional.of(follow));
        when(profileQueryService.findByUsername(eq(profileData.getUsername()), eq(user)))
                .thenReturn(Optional.of(profileData));

        given().header("Authorization", "Token " + token).when()
                .delete("/profiles/{username}/follow", anotherUser.getUsername()).prettyPeek().then().statusCode(200);

        verify(followRepository).removeRelation(eq(follow));
    }

}