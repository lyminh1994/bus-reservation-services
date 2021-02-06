package vn.com.minhlq.payload;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author Minh Ly Quang
 */
@Data
public class SignInRequest {

    @NotBlank(message = "Username can not be empty")
    private String username;

    @NotBlank(message = "Password can not be empty")
    private String password;

    private Boolean rememberMe = false;

}
