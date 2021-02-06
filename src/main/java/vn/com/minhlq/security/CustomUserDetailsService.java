package vn.com.minhlq.security;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.com.minhlq.entity.Permission;
import vn.com.minhlq.entity.Role;
import vn.com.minhlq.entity.User;
import vn.com.minhlq.repository.PermissionRepository;
import vn.com.minhlq.repository.RoleRepository;
import vn.com.minhlq.repository.UserRepository;
import vn.com.minhlq.vo.UserPrincipal;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Minh Ly Quang
 */
@Service
@Transactional
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userDao;

    private final RoleRepository roleDao;

    private final PermissionRepository permissionDao;

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userDao.findByUsernameOrEmailOrPhone(username, username, username)
                .orElseThrow(() -> new UsernameNotFoundException("User information not found: " + username));
        List<Role> roles = roleDao.selectByUserId(user.getId());
        List<Long> roleIds = roles.stream().map(Role::getId).collect(Collectors.toList());
        List<Permission> permissions = permissionDao.selectByRoleIdList(roleIds);
        return UserPrincipal.create(user, roles, permissions);
    }

}
