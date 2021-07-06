package vn.com.minhlq.security.services;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.RequestMethodsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import vn.com.minhlq.common.CommonConst;
import vn.com.minhlq.common.ResultCode;
import vn.com.minhlq.exception.SecurityException;
import vn.com.minhlq.model.Permission;
import vn.com.minhlq.model.Role;
import vn.com.minhlq.repository.PermissionRepository;
import vn.com.minhlq.repository.RoleRepository;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author minhlq
 */
@Component
@AllArgsConstructor
public class AuthorityService {

    private final RoleRepository roleRepository;

    private final PermissionRepository permissionRepository;

    private final RequestMappingHandlerMapping mapping;

    /**
     * Check account has permission to access system
     *
     * @param request        {@link HttpServletRequest}
     * @param authentication {@link Authentication}
     * @return This account has permission
     */
    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {
        checkRequest(request);

        Object userInfo = authentication.getPrincipal();
        if (userInfo instanceof UserDetails) {
            boolean hasPermission = false;

            UserPrincipal principal = (UserPrincipal) userInfo;
            Long userId = principal.getId();

            List<Role> roles = roleRepository.selectByUserId(userId);
            List<Long> roleIds = roles.stream().map(Role::getId).collect(Collectors.toList());
            List<Permission> permissions = permissionRepository.selectByListRoleIds(roleIds);

            // Obtain resources, front and back ends are separated, so page permissions are filtered, and only button permissions are reserved
            List<Permission> btnPerms = permissions.stream()
                    // Filter page permissions
                    .filter(permission -> Objects.equals(permission.getType(), CommonConst.BUTTON))
                    // Filter URL is empty
                    .filter(permission -> StringUtils.isNotBlank(permission.getUrl()))
                    // Filter Method is empty
                    .filter(permission -> StringUtils.isNotBlank(permission.getMethod()))
                    .collect(Collectors.toList());

            for (Permission btnPerm : btnPerms) {
                AntPathRequestMatcher antPathMatcher = new AntPathRequestMatcher(btnPerm.getUrl(), btnPerm.getMethod());
                if (antPathMatcher.matches(request)) {
                    hasPermission = true;
                    break;
                }
            }

            return hasPermission;
        } else {
            return false;
        }
    }

    /**
     * Verify that the request exists
     *
     * @param request {@link HttpServletRequest}
     */
    private void checkRequest(HttpServletRequest request) {
        // Get the current request method
        String currentMethod = request.getMethod();
        Multimap<String, String> urlMapping = allUrlMapping();

        for (String uri : urlMapping.keySet()) {
            // Match url through AntPathRequestMatcher
            // There are 2 ways to create AntPathRequestMatcher
            // 1：new AntPathRequestMatcher(uri, method) In this way, you can directly judge whether the method matches, because here we throw the method does not match custom throws, so we use the second method to create.
            // 2：new AntPathRequestMatcher(uri) This method does not verify the request method, only the request path.
            AntPathRequestMatcher antPathMatcher = new AntPathRequestMatcher(uri);
            if (antPathMatcher.matches(request)) {
                if (!urlMapping.get(uri).contains(currentMethod)) {
                    throw new SecurityException(ResultCode.HTTP_BAD_METHOD);
                } else {
                    return;
                }
            }
        }

        throw new SecurityException(ResultCode.REQUEST_NOT_FOUND);
    }

    /**
     * Get all URL Mapping, the return format is {"/test":["GET","POST"],"/sys":["GET","DELETE"]}
     *
     * @return {@link ArrayListMultimap} Format URL Mapping
     */
    private Multimap<String, String> allUrlMapping() {
        Multimap<String, String> urlMapping = ArrayListMultimap.create();

        // Get the corresponding information of url, class and method
        Map<RequestMappingInfo, HandlerMethod> handlerMethods = mapping.getHandlerMethods();

        handlerMethods.forEach((k, v) -> {
            // Get all URLs under the current key
            Set<String> url = k.getPatternsCondition().getPatterns();
            RequestMethodsRequestCondition method = k.getMethodsCondition();

            // Add all request methods for each URL
            url.forEach(s -> urlMapping.putAll(s, method.getMethods()
                    .stream()
                    .map(Enum::toString)
                    .collect(Collectors.toList())));
        });

        return urlMapping;
    }
}
