package vn.com.minhlq.api;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import vn.com.minhlq.config.JacksonConfig;
import vn.com.minhlq.model.User;
import vn.com.minhlq.repository.UserRepository;
import vn.com.minhlq.security.SecurityConfig;
import vn.com.minhlq.security.jwt.JwtUtils;
import vn.com.minhlq.service.UserService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UsersApi.class)
@Import({ SecurityConfig.class, JacksonConfig.class })
public class UsersApiTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private JwtUtils jwtService;

    @MockBean
    private UserService userService;

    @BeforeEach
    public void setUp() throws Exception {
        RestAssuredMockMvc.mockMvc(mvc);
    }

    @Test
    public void should_create_user_success() throws Exception {
        String email = "john@jacob.com";
        String username = "johnjacob";

        when(jwtService.createJWT(any(), false)).thenReturn("123");
        User user = new User(username, "123", "0911", email);

        when(userService.createUser(any())).thenReturn(user);

        when(userRepository.findByUsername(eq(username))).thenReturn(Optional.empty());
        when(userRepository.findByEmail(eq(email))).thenReturn(Optional.empty());

        Map<String, Object> param = prepareRegisterParameter(email, username);

        given().contentType("application/json").body(param).when().post("/users").then().statusCode(201)
                .body("user.email", equalTo(email)).body("user.username", equalTo(username))
                .body("user.token", equalTo("123"));

        verify(userService).createUser(any());
    }

    @Test
    public void should_show_error_message_for_blank_username() throws Exception {

        String email = "john@jacob.com";
        String username = "";

        Map<String, Object> param = prepareRegisterParameter(email, username);

        given().contentType("application/json").body(param).when().post("/users").prettyPeek().then().statusCode(422)
                .body("errors.username[0]", equalTo("can't be empty"));
    }

    @Test
    public void should_show_error_message_for_invalid_email() throws Exception {
        String email = "johnxjacob.com";
        String username = "johnjacob";

        Map<String, Object> param = prepareRegisterParameter(email, username);

        given().contentType("application/json").body(param).when().post("/users").prettyPeek().then().statusCode(422)
                .body("errors.email[0]", equalTo("should be an email"));
    }

    @Test
    public void should_show_error_for_duplicated_username() throws Exception {
        String email = "john@jacob.com";
        String username = "johnjacob";

        when(userRepository.findByUsername(eq(username)))
                .thenReturn(Optional.of(new User(username, "123", "0911", email)));
        when(userRepository.findByEmail(any())).thenReturn(Optional.empty());

        Map<String, Object> param = prepareRegisterParameter(email, username);

        given().contentType("application/json").body(param).when().post("/users").prettyPeek().then().statusCode(422)
                .body("errors.username[0]", equalTo("duplicated username"));
    }

    @Test
    public void should_show_error_for_duplicated_email() throws Exception {
        String email = "john@jacob.com";
        String username = "johnjacob2";

        when(userRepository.findByEmail(eq(email))).thenReturn(Optional.of(new User(username, "123", "0911", email)));

        when(userRepository.findByUsername(eq(username))).thenReturn(Optional.empty());

        Map<String, Object> param = prepareRegisterParameter(email, username);

        given().contentType("application/json").body(param).when().post("/users").then().statusCode(422)
                .body("errors.email[0]", equalTo("duplicated email"));
    }

    private HashMap<String, Object> prepareRegisterParameter(final String email, final String username) {
        return new HashMap<String, Object>() {
            {
                put("user", new HashMap<String, Object>() {
                    {
                        put("email", email);
                        put("password", "johnnyjacob");
                        put("username", username);
                    }
                });
            }
        };
    }

    @Test
    public void should_login_success() throws Exception {
        String email = "john@jacob.com";
        String username = "johnjacob2";
        String password = "123";

        User user = new User(username, password, "0911", email);

        when(userRepository.findByEmail(eq(email))).thenReturn(Optional.of(user));
        when(jwtService.createJWT(any(), false)).thenReturn("123");

        Map<String, Object> param = new HashMap<String, Object>() {
            {
                put("user", new HashMap<String, Object>() {
                    {
                        put("email", email);
                        put("password", password);
                    }
                });
            }
        };

        given().contentType("application/json").body(param).when().post("/users/login").then().statusCode(200)
                .body("user.email", equalTo(email)).body("user.username", equalTo(username))
                .body("user.token", equalTo("123"));
        ;
    }

    @Test
    public void should_fail_login_with_wrong_password() throws Exception {
        String email = "john@jacob.com";
        String username = "johnjacob2";
        String password = "123";

        User user = new User(username, password, "0911", email);

        when(userRepository.findByEmail(eq(email))).thenReturn(Optional.of(user));

        Map<String, Object> param = new HashMap<String, Object>() {
            {
                put("user", new HashMap<String, Object>() {
                    {
                        put("email", email);
                        put("password", "123123");
                    }
                });
            }
        };

        given().contentType("application/json").body(param).when().post("/users/login").prettyPeek().then()
                .statusCode(422).body("message", equalTo("invalid email or password"));
    }

}
