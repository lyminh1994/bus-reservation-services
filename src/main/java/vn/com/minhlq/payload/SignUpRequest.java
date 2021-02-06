package vn.com.minhlq.payload;

import lombok.Data;
import vn.com.minhlq.validation.ValidEmail;

import javax.validation.constraints.NotBlank;

/**
 * @author Minh Ly Quang
 */
@Data
public class SignUpRequest {

    @NotBlank
    private String username;

    @NotBlank
    @ValidEmail
    private String email;

    @NotBlank
    private String password;

}
