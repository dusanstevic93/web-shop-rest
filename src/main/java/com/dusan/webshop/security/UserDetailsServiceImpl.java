package com.dusan.webshop.security;

import com.dusan.webshop.dao.repository.UserRepository;
import com.dusan.webshop.dao.projection.AuthenticationProjection;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AuthenticationProjection projection = userRepository.getAuthenticationProjection(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username " + username + " does not exist"));
        return new UserDetailsImpl(projection);
    }
}
