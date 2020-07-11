package com.dusan.webshop.security;

import com.dusan.webshop.dao.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers(HttpMethod.POST, "/brands/**", "/categories/**", "/products/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/brands/**", "/categories/**", "/products/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/products/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/orders/**").hasRole("CUSTOMER")
                .antMatchers(HttpMethod.PUT, "/orders/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/orders/**").hasRole("ADMIN")
                .antMatchers("/customers/me").hasRole("CUSTOMER")
                .antMatchers(HttpMethod.GET, "/customers/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/registration/customer").anonymous()
                .anyRequest().permitAll();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public UserDetailsServiceImpl userDetailsService(UserRepository userRepository) {
        return new UserDetailsServiceImpl(userRepository);
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
