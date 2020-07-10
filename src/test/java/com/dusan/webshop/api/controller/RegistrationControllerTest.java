package com.dusan.webshop.api.controller;

import com.dusan.webshop.dto.request.CustomerRegistrationRequest;
import com.dusan.webshop.service.RegistrationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RegistrationController.class)
class RegistrationControllerTest extends ControllerTestSetup {

    @MockBean
    private RegistrationService registrationService;

    @Autowired
    private MockMvc mvc;

    @Test
    @WithAnonymousUser
    void registerCustomerAnonymousUser() throws Exception {
        CustomerRegistrationRequest request = new CustomerRegistrationRequest();
        request.setUsername("test");
        request.setPassword("test");
        request.setFirstName("test");
        request.setLastName("test");
        request.setEmail("test@mail.com");
        request.setPhone("test");
        request.setStreet("test");
        request.setCity("test");
        request.setZipCode("test");

        String json = new ObjectMapper().writeValueAsString(request);

        mvc.perform(post("/registration/customer").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void registerCustomerAdmin() throws Exception {
        mvc.perform(post("/registration/customer").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "CUSTOMER")
    void registerCustomerCustomer() throws Exception {
        mvc.perform(post("/registration/customer").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }
}