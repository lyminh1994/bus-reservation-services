package vn.com.minhlq.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import vn.com.minhlq.exception.SecurityException;
import vn.com.minhlq.payload.SignInRequest;
import vn.com.minhlq.payload.SignUpRequest;
import vn.com.minhlq.security.jwt.JwtResponse;
import vn.com.minhlq.security.jwt.JwtUtil;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * @author Minh Ly Quang
 */
@RestController
@AllArgsConstructor
public class AuthController {

    private final JwtUtil jwtUtil;

    private final AuthenticationManager authenticationManager;

    @PostMapping("/auth/login")
    public ResponseEntity<JwtResponse> login(@Valid @RequestBody SignInRequest signInRequest) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                signInRequest.getUsername(),
                signInRequest.getPassword()
        );
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtUtil.createJWT(authentication, signInRequest.getRememberMe());
        return ResponseEntity.ok(new JwtResponse(jwt));
    }

    @PostMapping("/auth/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        try {
            // Set JWT expiration
            jwtUtil.invalidateJWT(request);
        } catch (SecurityException e) {
            throw new SecurityException(HttpStatus.UNAUTHORIZED);
        }

        return ResponseEntity.ok().body("Logout successfully");
    }

    @PostMapping("/auth/register")
    public ResponseEntity<String> register(@Valid @RequestBody SignUpRequest signUpRequest) {
        return ResponseEntity.ok("register");
    }

    @PostMapping("/auth/verify")
    public ResponseEntity<String> verify() {
        return ResponseEntity.ok("verify");
    }

    @PostMapping("/auth/verify-resend")
    public ResponseEntity<String> verifyResend() {
        return ResponseEntity.ok("verify-resend");
    }

    @PostMapping("/auth/forgot-password")
    public ResponseEntity<String> forgotPassword() {
        return ResponseEntity.ok("forgot-password");
    }

    @PostMapping("/auth/recover-password")
    public ResponseEntity<String> recoverPassword() {
        return ResponseEntity.ok("recover-password");
    }

}
