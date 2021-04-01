package vn.com.minhlq.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.access.AccessDeniedHandler;
import vn.com.minhlq.common.ResultCode;
import vn.com.minhlq.utils.ResponseUtil;

/**
 * @author MinhLQ
 */
@Configuration
public class SecurityHandlerConfig {

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return (request, response, accessDeniedException) -> ResponseUtil.renderJson(response, ResultCode.ACCESS_DENIED, null);
    }

}
