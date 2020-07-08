package com.dusan.webshop.api.controller;

import com.dusan.webshop.dao.UserRepository;
import org.springframework.boot.test.mock.mockito.MockBean;

class ControllerTestSetup {

    @MockBean
    private UserRepository userRepository;
}
