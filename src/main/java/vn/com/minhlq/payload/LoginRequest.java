package vn.com.minhlq.payload;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author minhlq
 */
@Data
public class LoginRequest {

    @NotBlank(message = "Username can not be empty")
    private String username;

    @NotBlank(message = "Password can not be empty")
    private String password;

    private Boolean rememberMe = false;

}
