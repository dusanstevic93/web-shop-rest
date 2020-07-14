package com.dusan.webshop.api.controller;

import com.dusan.webshop.dto.request.UpdateCustomerRequest;
import com.dusan.webshop.dto.response.CustomerResponse;
import com.dusan.webshop.service.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CustomerController.class)
class CustomerControllerTest extends ControllerTestSetup {

    @MockBean
    private CustomerService customerService;

    @Autowired
    private MockMvc mvc;

    @Test
    @WithCustomMockUser(roles = "CUSTOMER")
    void updateCustomerRoleCustomer() throws Exception {
        UpdateCustomerRequest request = new UpdateCustomerRequest();
        request.setUsername("test");
        request.setFirstName("test");
        request.setLastName("test");
        request.setEmail("test@mail.com");
        request.setPhone("test");
        request.setStreet("test");
        request.setCity("test");
        request.setZipCode("test");

        String json = new ObjectMapper().writeValueAsString(request);

        mvc.perform(put("/customers/me").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void updateCustomerRoleAdmin() throws Exception {
        mvc.perform(put("/customers/me").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithAnonymousUser
    void updateCustomerAnonymousUser() throws Exception {
        mvc.perform(put("/customers/me").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void getAnyCustomerRoleAdmin() throws Exception {
        given(customerService.findCustomerById(anyLong())).willReturn(new CustomerResponse());

        mvc.perform(get("/customers/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "CUSTOMER")
    void getAnyCustomerRoleCustomer() throws Exception {
        mvc.perform(get("/customers/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithAnonymousUser
    void getAnyCustomerAnonymousUser() throws Exception {
        mvc.perform(get("/customers/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithCustomMockUser(roles = "CUSTOMER")
    void getAuthenticatedCustomerRoleCustomer() throws Exception {
        given(customerService.findCustomerById(anyLong())).willReturn(new CustomerResponse());

        mvc.perform(get("/customers/me").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void getAuthenticatedCustomerRoleAdmin() throws Exception {
        mvc.perform(get("/customers/me").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithAnonymousUser
    void getAuthenticatedCustomerAnonymousUser() throws Exception {
        mvc.perform(get("/customers/me").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void getAllCustomersRoleAdmin() throws Exception {
        given(customerService.findAllCustomers(any())).willReturn(new PageImpl<>(Collections.emptyList()));

        mvc.perform(get("/customers").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "CUSTOMER")
    void getAllCustomersRoleCustomer() throws Exception {
        mvc.perform(get("/customers").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithAnonymousUser
    void getAllCustomersAnonymousUser() throws Exception {
        mvc.perform(get("/customers").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }
}