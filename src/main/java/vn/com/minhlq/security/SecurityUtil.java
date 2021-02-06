package vn.com.minhlq.security;

import lombok.experimental.UtilityClass;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.ObjectUtils;
import vn.com.minhlq.common.CommonConst;
import vn.com.minhlq.vo.UserPrincipal;

/**
 * @author Minh Ly Quang
 */
@UtilityClass
public class SecurityUtil {

    /**
     * Get the username of the currently login user
     *
     * @return Username of currently login user
     */
    public String getCurrentUsername() {
        UserPrincipal currentUser = getCurrentUser();
        return ObjectUtils.isEmpty(currentUser) ? CommonConst.ANONYMOUS_NAME : currentUser.getUsername();
    }

    /**
     * Get current login user information
     *
     * @return The information of the currently login user, null when login anonymous
     */
    public UserPrincipal getCurrentUser() {
        Object userInfo = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (userInfo instanceof UserDetails) {
            return (UserPrincipal) userInfo;
        }

        return null;
    }

}
