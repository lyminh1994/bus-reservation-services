package vn.com.minhlq.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Minh Ly Quang
 */
@Data
@ConfigurationProperties(prefix = "custom.config")
public class CustomConfig {

    private IgnoreConfig ignores;

}
