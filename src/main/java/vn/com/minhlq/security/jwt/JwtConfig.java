package vn.com.minhlq.security.jwt;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Minh Ly Quang
 */
@Data
@ConfigurationProperties(prefix = "jwt.config")
public class JwtConfig {

    /**
     * JWT secret key，default value：boilerplate.
     */
    private String secret = "boilerplate";

    /**
     * JWT expire time，default value：600000({@code 10 minutes}).
     */
    private Long ttl = 600000L;

    /**
     * Turn on Remember me to add more jwt expiration time, default value: 604800000({@code 7 days}).
     */
    private Long remember = 604800000L;

}
