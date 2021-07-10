package vn.com.minhlq.dto;

import com.fasterxml.jackson.annotation.JsonRootName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import vn.com.minhlq.validation.EmailConstraint;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonRootName("user")
public class UpdateUserParam {

    @Builder.Default
    @EmailConstraint
    private String email = "";

    @Builder.Default
    private String password = "";

    @Builder.Default
    private String username = "";

}
