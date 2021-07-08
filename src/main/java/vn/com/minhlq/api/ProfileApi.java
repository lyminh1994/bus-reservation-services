package vn.com.minhlq.api;

import java.util.HashMap;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import vn.com.minhlq.dto.ProfileData;
import vn.com.minhlq.exception.ResourceNotFoundException;
import vn.com.minhlq.model.Follow;
import vn.com.minhlq.model.User;
import vn.com.minhlq.repository.UserRepository;
import vn.com.minhlq.service.ProfileQueryService;

@Tag(name = "Profiles")
@RestController
@RequiredArgsConstructor
@RequestMapping("/profiles/{username}")
public class ProfileApi {

    private ProfileQueryService profileQueryService;

    private UserRepository userRepository;

    @GetMapping
    public ResponseEntity<?> getProfile(@PathVariable("username") String username, @AuthenticationPrincipal User user) {
        return profileQueryService.findByUsername(username, user).map(this::profileResponse)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @PostMapping("/follow")
    public ResponseEntity<?> follow(@PathVariable("username") String username, @AuthenticationPrincipal User user) {
        return userRepository.findByUsername(username).map(target -> {
            Follow follow = new Follow(user.getId(), target.getId());
            userRepository.saveRelation(follow);
            return profileResponse(profileQueryService.findByUsername(username, user).get());
        }).orElseThrow(ResourceNotFoundException::new);
    }

    @DeleteMapping("/follow")
    public ResponseEntity<?> unFollow(@PathVariable("username") String username, @AuthenticationPrincipal User user) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isPresent()) {
            User target = userOptional.get();
            return userRepository.findRelation(user.getId(), target.getId()).map(relation -> {
                userRepository.removeRelation(relation);
                return profileResponse(profileQueryService.findByUsername(username, user).get());
            }).orElseThrow(ResourceNotFoundException::new);
        } else {
            throw new ResourceNotFoundException();
        }
    }

    private ResponseEntity<?> profileResponse(ProfileData profile) {
        return ResponseEntity.ok(new HashMap<String, Object>() {
            {
                put("profile", profile);
            }
        });
    }

}
