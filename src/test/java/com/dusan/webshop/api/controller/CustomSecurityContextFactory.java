package com.dusan.webshop.api.controller;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class CustomSecurityContextFactory implements WithSecurityContextFactory<WithCustomMockUser> {

    @Override
    public SecurityContext createSecurityContext(WithCustomMockUser withCustomMockUser) {

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        long userId = withCustomMockUser.userId();
        Set<SimpleGrantedAuthority> authorities = Arrays.stream(withCustomMockUser.roles())
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toSet());
        Authentication authentication = new UsernamePasswordAuthenticationToken(userId, null, authorities);
        context.setAuthentication(authentication);
        return context;
    }
}
