package vn.com.minhlq.security.jwt;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author minhlq
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtResponse {

    private String token;

    private String tokenType = "Bearer";

    public JwtResponse(String token) {
        this.token = token;
    }

}
