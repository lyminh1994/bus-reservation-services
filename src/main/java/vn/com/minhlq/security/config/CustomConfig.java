package vn.com.minhlq.security.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author minhlq
 */
@Data
@ConfigurationProperties(prefix = "custom.config")
public class CustomConfig {
    /**
     * Url that do not need to be intercepted
     */
    private IgnoreConfig ignores;

}
