package vn.com.minhlq.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.com.minhlq.common.R;
import vn.com.minhlq.common.ResultCode;
import vn.com.minhlq.exception.SecurityException;
import vn.com.minhlq.payload.LoginRequest;
import vn.com.minhlq.payload.RegisterRequest;
import vn.com.minhlq.security.jwt.JwtResponse;
import vn.com.minhlq.security.jwt.JwtUtils;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * @author minhlq
 */

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication")
public class AuthController {

    private final JwtUtils jwtUtils;

    private final AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public R<JwtResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(), loginRequest.getPassword());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtUtils.createJWT(authentication, loginRequest.getRememberMe());
        return R.ofSuccess(new JwtResponse(jwt));
    }

    @PostMapping("/logout")
    public R<?> logout(HttpServletRequest request) {
        try {
            // Set JWT expiration
            jwtUtils.invalidateJWT(request);
        } catch (SecurityException e) {
            throw new SecurityException(ResultCode.UNAUTHORIZED);
        }

        return R.ofCode(ResultCode.LOGOUT);
    }

    @PostMapping("/register")
    public R<?> register(@Valid @RequestBody RegisterRequest registerRequest) {
        log.info("register");
        return null;
    }

    @PostMapping("/verify")
    public R<?> verify() {
        log.info("verify");
        return null;
    }

    @PostMapping("/verify-resend")
    public R<?> verifyResend() {
        log.info("verify-resend");
        return null;
    }

    @PostMapping("/forgot-password")
    public R<?> forgotPassword() {
        log.info("forgot-password");
        return null;
    }

    @PostMapping("/recover-password")
    public R<?> recoverPassword() {
        log.info("recover-password");
        return null;
    }

}
