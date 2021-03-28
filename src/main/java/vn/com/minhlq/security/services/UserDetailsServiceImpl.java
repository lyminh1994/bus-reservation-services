package vn.com.minhlq.security.services;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.com.minhlq.models.Permission;
import vn.com.minhlq.models.Role;
import vn.com.minhlq.models.User;
import vn.com.minhlq.repositories.PermissionRepository;
import vn.com.minhlq.repositories.RoleRepository;
import vn.com.minhlq.repositories.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author MinhLQ
 */
@Service
@Transactional
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PermissionRepository permissionRepository;

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmailOrPhone) {
        User user = userRepository.findByUsernameOrEmailOrPhone(usernameOrEmailOrPhone, usernameOrEmailOrPhone, usernameOrEmailOrPhone)
                .orElseThrow(() -> new UsernameNotFoundException("User information not found: " + usernameOrEmailOrPhone));
        List<Role> roles = roleRepository.selectByUserId(user.getId());
        List<Long> roleIds = roles.stream().map(Role::getId).collect(Collectors.toList());
        List<Permission> permissions = permissionRepository.selectByRoleIdList(roleIds);
        return UserPrincipal.create(user, roles, permissions);
    }

}
