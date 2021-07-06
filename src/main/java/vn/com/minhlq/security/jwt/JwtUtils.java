package vn.com.minhlq.security.jwt;

import io.jsonwebtoken.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import vn.com.minhlq.common.CommonConst;
import vn.com.minhlq.common.ResultCode;
import vn.com.minhlq.exception.SecurityException;
import vn.com.minhlq.security.services.UserPrincipal;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author minhlq
 */
@Slf4j
@Configuration
@AllArgsConstructor
@EnableConfigurationProperties(JwtConfig.class)
public class JwtUtils {

    private static final String ROLES_KEY = "roles";

    private static final String AUTHORITIES_KEY = "auth";

    public static final String AUTHORIZATION_HEADER = "Authorization";

    private final JwtConfig jwtConfig;

    private final StringRedisTemplate stringRedisTemplate;

    /**
     * Create JWT string from information of login user
     *
     * @param rememberMe  Remember me
     * @param id          User id
     * @param subject     Username
     * @param roles       User roles
     * @param authorities User authorities
     * @return JWT string
     */
    public String createJWT(Boolean rememberMe, Long id, String subject, List<String> roles, Collection<? extends GrantedAuthority> authorities) {
        Date now = new Date();
        JwtBuilder builder = Jwts.builder()
                .setId(id.toString())
                .setSubject(subject)
                .setIssuedAt(now)
                .signWith(SignatureAlgorithm.HS256, jwtConfig.getSecret())
                .claim(ROLES_KEY, roles)
                .claim(AUTHORITIES_KEY, authorities);

        // Set expiration time
        Long ttl = rememberMe ? jwtConfig.getRemember() : jwtConfig.getTtl();
        if (ttl > 0) {
            builder.setExpiration(DateUtils.addMilliseconds(now, ttl.intValue()));
        }

        String jwt = builder.compact();
        // Save the generated JWT into Redis
        stringRedisTemplate.opsForValue().set(CommonConst.REDIS_JWT_KEY_PREFIX + subject, jwt, ttl,
                TimeUnit.MILLISECONDS);

        return jwt;
    }

    /**
     * Create JWT string
     *
     * @param authentication User authentication information
     * @param rememberMe     Remember me
     * @return JWT
     */
    public String createJWT(Authentication authentication, Boolean rememberMe) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        return createJWT(rememberMe, userPrincipal.getId(), userPrincipal.getUsername(), userPrincipal.getRoles(), userPrincipal.getAuthorities());
    }

    /**
     * Parse JWT
     *
     * @param jwt JWT string
     * @return {@link Claims}
     */
    public Claims parseJWT(String jwt) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(jwtConfig.getSecret())
                    .parseClaimsJws(jwt)
                    .getBody();

            String username = claims.getSubject();
            String redisKey = CommonConst.REDIS_JWT_KEY_PREFIX + username;

            // Check whether the JWT in redis exists
            Long expire = stringRedisTemplate.getExpire(redisKey, TimeUnit.MILLISECONDS);
            if (Objects.isNull(expire) || expire <= 0) {
                throw new SecurityException(ResultCode.TOKEN_EXPIRED);
            }

            // Check whether the JWT in redis is consistent with the current one.
            // Inconsistency means that the user has logout/the user is login on a different device, which means that the JWT has expired
            String redisToken = stringRedisTemplate.opsForValue().get(redisKey);
            if (!StringUtils.equals(jwt, redisToken)) {
                throw new SecurityException(ResultCode.TOKEN_OUT_OF_CTRL);
            }

            return claims;
        } catch (ExpiredJwtException e) {
            log.error("JWT token expired: {}", e.getMessage());
            throw new SecurityException(ResultCode.TOKEN_EXPIRED);
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
            throw new SecurityException(ResultCode.TOKEN_PARSE_ERROR);
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
            throw new SecurityException(ResultCode.TOKEN_PARSE_ERROR);
        } catch (SignatureException e) {
            log.error("Invalid JWT signature: {}", e.getMessage());
            throw new SecurityException(ResultCode.TOKEN_PARSE_ERROR);
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
            throw new SecurityException(ResultCode.TOKEN_PARSE_ERROR);
        }
    }

    /**
     * Get username according to JWT
     *
     * @param jwt JWT string
     * @return username
     */
    public String getUsernameFromJWT(String jwt) {
        Claims claims = parseJWT(jwt);
        return claims.getSubject();
    }

    /**
     * Get the JWT from the request header
     *
     * @param request Request
     * @return JWT string
     */
    public String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.isNotBlank(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }

        return null;
    }

    /**
     * Set JWT expiration
     *
     * @param request Request
     */
    public void invalidateJWT(HttpServletRequest request) {
        String jwt = getJwtFromRequest(request);
        String username = getUsernameFromJWT(jwt);
        // Clear JWT from redis
        stringRedisTemplate.delete(CommonConst.REDIS_JWT_KEY_PREFIX + username);
    }
}
