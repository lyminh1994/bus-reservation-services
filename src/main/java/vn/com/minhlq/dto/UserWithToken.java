package vn.com.minhlq.dto;

import lombok.Getter;

@Getter
public class UserWithToken {

    private String email;

    private String username;

    private String token;

    public UserWithToken(UserData userData, String token) {
        this.email = userData.getEmail();
        this.username = userData.getUsername();
        this.token = token;
    }

}
