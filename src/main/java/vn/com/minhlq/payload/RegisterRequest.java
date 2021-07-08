package vn.com.minhlq.payload;

import lombok.Data;
import vn.com.minhlq.validation.EmailConstraint;

import javax.validation.constraints.NotBlank;

/**
 * @author minhlq
 */
@Data
public class RegisterRequest {

    @NotBlank
    private String username;

    @NotBlank
    @EmailConstraint
    private String email;

    @NotBlank
    private String password;

}
