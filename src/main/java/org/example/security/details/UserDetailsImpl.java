package org.example.security.details;

import org.example.models.entity.Permission;
import org.example.models.entity.Role;
import org.example.models.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class UserDetailsImpl implements UserDetails {

    private String username;
    private String password;
    private boolean enabled = true;

    private Set<Permission> authorities = new HashSet<>();


    public UserDetailsImpl(String username, String password, boolean enabled, Set<Permission> authorities) {
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.authorities = authorities;
    }


    public static UserDetailsImpl build(User user){
        return new UserDetailsImpl(
                user.getLogin(),
                user.getPassword(),
                true,
                user.getRoles()
                        .stream()
                        .map(Role::getPermissions)
                        .flatMap(permission -> permission.stream())
                        .collect(Collectors.toSet())
        );
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
        return enabled;
    }

    @Override
    public boolean isAccountNonLocked() {
        return enabled;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return enabled;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
