package com.dusan.webshop.security;

import com.dusan.webshop.dao.projection.AuthenticationProjection;
import com.dusan.webshop.entity.enums.UserRoles;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
public class UserDetailsImpl implements UserDetails {

    private final AuthenticationProjection authenticationProjection;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String role = UserRoles.fromId(authenticationProjection.getRoleId()).name();
        Set<SimpleGrantedAuthority> roles = new HashSet<>();
        roles.add(new SimpleGrantedAuthority("ROLE_" + role));
        return roles;
    }

    @Override
    public String getPassword() {
        return authenticationProjection.getPassword();
    }

    @Override
    public String getUsername() {
        return authenticationProjection.getUsername();
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
        return true;
    }

    public long getUserId() {
        return authenticationProjection.getUserId();
    }
}
