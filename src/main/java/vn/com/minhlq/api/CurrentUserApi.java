package vn.com.minhlq.api;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import vn.com.minhlq.dto.UpdateUserCommand;
import vn.com.minhlq.dto.UpdateUserParam;
import vn.com.minhlq.dto.UserData;
import vn.com.minhlq.dto.UserWithToken;
import vn.com.minhlq.model.User;
import vn.com.minhlq.service.UserQueryService;
import vn.com.minhlq.service.UserService;

@Tag(name = "Current User")
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/user")
public class CurrentUserApi {

    private UserQueryService userQueryService;

    private UserService userService;

    @GetMapping
    public ResponseEntity<?> currentUser(@AuthenticationPrincipal User currentUser,
            @RequestHeader(value = "Authorization") String authorization) {
        UserData userData = userQueryService.findById(currentUser.getId()).get();
        return ResponseEntity.ok(userResponse(new UserWithToken(userData, authorization.split(" ")[1])));
    }

    @PutMapping
    public ResponseEntity<?> updateProfile(@AuthenticationPrincipal User currentUser,
            @RequestHeader("Authorization") String token, @Valid @RequestBody UpdateUserParam updateUserParam) {

        userService.updateUser(new UpdateUserCommand(currentUser, updateUserParam));
        UserData userData = userQueryService.findById(currentUser.getId()).get();
        return ResponseEntity.ok(userResponse(new UserWithToken(userData, token.split(" ")[1])));
    }

    private Map<String, Object> userResponse(UserWithToken userWithToken) {
        return new HashMap<String, Object>() {
            {
                put("user", userWithToken);
            }
        };
    }

}
