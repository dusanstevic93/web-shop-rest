package com.dusan.webshop.api.controller;

import com.dusan.webshop.dao.UserRepository;
import com.dusan.webshop.security.JWTAuthenticationFilter;
import com.dusan.webshop.security.JWTUtils;
import org.springframework.boot.test.mock.mockito.MockBean;

class ControllerTestSetup {

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private JWTUtils jwtUtils;
}
