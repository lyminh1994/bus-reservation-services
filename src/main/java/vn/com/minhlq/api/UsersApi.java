package vn.com.minhlq.api;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import vn.com.minhlq.dto.LoginParam;
import vn.com.minhlq.dto.RegisterParam;
import vn.com.minhlq.dto.UserData;
import vn.com.minhlq.dto.UserWithToken;
import vn.com.minhlq.exception.InvalidAuthenticationException;
import vn.com.minhlq.model.User;
import vn.com.minhlq.repository.UserRepository;
import vn.com.minhlq.service.UserQueryService;
import vn.com.minhlq.service.UserService;
import vn.com.minhlq.utils.CryptoUtils;

@Tag(name = "Users")
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UsersApi {

    private UserRepository userRepository;

    private UserQueryService userQueryService;

    private UserService userService;

    @PostMapping
    public ResponseEntity<?> createUser(@Valid @RequestBody RegisterParam registerParam) {
        User user = userService.createUser(registerParam);
        UserData userData = userQueryService.findById(user.getId()).get();
        return ResponseEntity.status(201).body(userResponse(new UserWithToken(userData, "jwtService.toToken(user)")));
    }

    @PostMapping("/login")
    public ResponseEntity<?> userLogin(@Valid @RequestBody LoginParam loginParam) {
        Optional<User> optional = userRepository.findByEmail(loginParam.getEmail());
        if (optional.isPresent() && CryptoUtils.check(loginParam.getPassword(), optional.get().getPassword())) {
            UserData userData = userQueryService.findById(optional.get().getId()).get();
            return ResponseEntity.ok(userResponse(new UserWithToken(userData, "JwtUtils.toToken(optional.get())")));
        } else {
            throw new InvalidAuthenticationException();
        }
    }

    private Map<String, Object> userResponse(UserWithToken userWithToken) {
        return new HashMap<String, Object>() {
            {
                put("user", userWithToken);
            }
        };
    }

}
