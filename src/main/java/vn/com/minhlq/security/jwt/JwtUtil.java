package vn.com.minhlq.security.jwt;

import io.jsonwebtoken.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import vn.com.minhlq.common.CommonConst;
import vn.com.minhlq.exception.SecurityException;
import vn.com.minhlq.vo.UserPrincipal;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author Minh Ly Quang
 */
@Slf4j
@Configuration
@AllArgsConstructor
@EnableConfigurationProperties(JwtConfig.class)
public class JwtUtil {

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
    public String createJWT(boolean rememberMe, Long id, String subject, List<String> roles, Collection<? extends GrantedAuthority> authorities) {
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
        stringRedisTemplate.opsForValue().set(CommonConst.REDIS_JWT_KEY_PREFIX + subject, jwt, ttl, TimeUnit.MILLISECONDS);

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
                throw new SecurityException(HttpStatus.INTERNAL_SERVER_ERROR, "Token has expired, please try to login again");
            }

            // Check whether the JWT in redis is consistent with the current one.
            // Inconsistency means that the user has logout/the user is login on a different device, which means that the JWT has expired
            String redisToken = stringRedisTemplate.opsForValue().get(redisKey);
            if (!StringUtils.equals(jwt, redisToken)) {
                throw new SecurityException(HttpStatus.INTERNAL_SERVER_ERROR, "Your account has login somewhere, please change password or try to login again");
            }

            return claims;
        } catch (ExpiredJwtException e) {
            log.error("Token expired");
            throw new SecurityException(HttpStatus.INTERNAL_SERVER_ERROR, "Token expired");
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported Token");
            throw new SecurityException(HttpStatus.INTERNAL_SERVER_ERROR, "Unsupported Token");
        } catch (MalformedJwtException e) {
            log.error("Token invalid");
            throw new SecurityException(HttpStatus.INTERNAL_SERVER_ERROR, "Token invalid");
        } catch (SignatureException e) {
            log.error("Invalid Token signature");
            throw new SecurityException(HttpStatus.INTERNAL_SERVER_ERROR, "Invalid Token signature");
        } catch (IllegalArgumentException e) {
            log.error("Token parameter does not exist");
            throw new SecurityException(HttpStatus.INTERNAL_SERVER_ERROR, "Token parameter does not exist");
        }
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

}
