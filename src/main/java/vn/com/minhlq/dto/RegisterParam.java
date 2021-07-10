package vn.com.minhlq.dto;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonRootName;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import vn.com.minhlq.validation.DuplicatedEmailConstraint;
import vn.com.minhlq.validation.DuplicatedUsernameConstraint;
import vn.com.minhlq.validation.EmailConstraint;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonRootName("user")
public class RegisterParam {

    @NotBlank(message = "Email can't be empty")
    @EmailConstraint
    @DuplicatedEmailConstraint
    private String email;

    @NotBlank(message = "Username can't be empty")
    @DuplicatedUsernameConstraint
    private String username;

    @NotBlank(message = "Password can't be empty")
    private String password;

}
