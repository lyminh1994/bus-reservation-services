package vn.com.minhlq.security.services;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import vn.com.minhlq.common.CommonConst;
import vn.com.minhlq.model.Permission;
import vn.com.minhlq.model.Role;
import vn.com.minhlq.model.User;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author minhlq
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPrincipal implements UserDetails {

    private Long id;

    private String username;

    @JsonIgnore
    private String password;

    private String firsName;

    private String lastName;

    private String phone;

    private String email;

    private DateTime birthday;

    private Integer sex;

    private Integer status;

    private DateTime createAt;

    private DateTime updateAt;

    private List<String> roles;

    /**
     * List user permission
     */
    private Collection<? extends GrantedAuthority> authorities;

    public static UserPrincipal create(User user, List<Role> roles, List<Permission> permissions) {
        List<String> roleNames = roles.stream().map(Role::getName).collect(Collectors.toList());

        List<GrantedAuthority> authorities = permissions.stream()
                .filter(permission -> StringUtils.isNotBlank(permission.getExpression()))
                .map(permission -> new SimpleGrantedAuthority(permission.getExpression())).collect(Collectors.toList());

        return new UserPrincipal(user.getId(), user.getUsername(), user.getPassword(), user.getFirstName(),
                user.getLastName(), user.getPhone(), user.getEmail(), user.getBirthday(), user.getGender(),
                user.getStatus(), user.getCreateAt(), user.getUpdateAt(), roleNames, authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return Objects.equals(this.status, CommonConst.ENABLE);
    }

}
