package vn.com.minhlq.dto;

import javax.validation.constraints.Email;

import com.fasterxml.jackson.annotation.JsonRootName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonRootName("user")
public class UpdateUserParam {

    @Builder.Default
    @Email(message = "should be an email")
    private String email = "";

    @Builder.Default
    private String password = "";

    @Builder.Default
    private String username = "";

}
