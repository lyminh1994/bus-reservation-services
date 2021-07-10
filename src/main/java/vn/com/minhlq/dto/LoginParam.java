package vn.com.minhlq.dto;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonRootName;

import lombok.Getter;
import lombok.NoArgsConstructor;
import vn.com.minhlq.validation.EmailConstraint;

@Getter
@NoArgsConstructor
@JsonRootName("user")
public class LoginParam {

    @NotBlank(message = "Email can't be empty")
    @EmailConstraint
    private String email;

    @NotBlank(message = "Password can't be empty")
    private String password;

}
