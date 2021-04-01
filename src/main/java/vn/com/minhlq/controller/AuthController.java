package vn.com.minhlq.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.com.minhlq.common.ApiResponse;
import vn.com.minhlq.common.ResultCode;
import vn.com.minhlq.exception.SecurityException;
import vn.com.minhlq.payload.LoginRequest;
import vn.com.minhlq.payload.RegisterRequest;
import vn.com.minhlq.security.jwt.JwtResponse;
import vn.com.minhlq.security.jwt.JwtUtils;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * @author MinhLQ
 */

@AllArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtUtils jwtUtils;

    private final AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ApiResponse login(@Valid @RequestBody LoginRequest loginRequest) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(),
                loginRequest.getPassword()
        );
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtUtils.createJWT(authentication, loginRequest.getRememberMe());
        return ApiResponse.ofSuccess(new JwtResponse(jwt));
    }

    @PostMapping("/logout")
    public ApiResponse logout(HttpServletRequest request) {
        try {
            // Set JWT expiration
            jwtUtils.invalidateJWT(request);
        } catch (SecurityException e) {
            throw new SecurityException(ResultCode.UNAUTHORIZED);
        }

        return ApiResponse.ofCode(ResultCode.LOGOUT);
    }

    @PostMapping("/register")
    public ApiResponse register(@Valid @RequestBody RegisterRequest registerRequest) {
        return ApiResponse.ofSuccess("register");
    }

    @PostMapping("/verify")
    public ApiResponse verify() {
        return ApiResponse.ofSuccess("verify");
    }

    @PostMapping("/verify-resend")
    public ApiResponse verifyResend() {
        return ApiResponse.ofSuccess("verify-resend");
    }

    @PostMapping("/forgot-password")
    public ApiResponse forgotPassword() {
        return ApiResponse.ofSuccess("forgot-password");
    }

    @PostMapping("/recover-password")
    public ApiResponse recoverPassword() {
        return ApiResponse.ofSuccess("recover-password");
    }

}
