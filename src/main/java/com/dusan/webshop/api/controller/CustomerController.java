package com.dusan.webshop.api.controller;

import com.dusan.webshop.dto.request.UpdateCustomerRequest;
import com.dusan.webshop.dto.request.params.PageParams;
import com.dusan.webshop.dto.response.CustomerResponse;
import com.dusan.webshop.dto.response.PageResponseWrapper;
import com.dusan.webshop.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping("/customers")
public class CustomerController {

    private CustomerService customerService;

    @PutMapping(value = "/me", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void updateCustomer(@Valid @RequestBody UpdateCustomerRequest request) {
        customerService.updateCustomer(1, request);
    }

    @GetMapping(value = "/{customerId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CustomerResponse getAnyCustomer(@PathVariable long customerId) {
        return customerService.findCustomerById(customerId);
    }

    @GetMapping(value = "/me", produces = MediaType.APPLICATION_JSON_VALUE)
    public CustomerResponse getAuthenticatedCustomer() {
        return customerService.findCustomerById(1);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public PageResponseWrapper<CustomerResponse> getAllCustomers(@Valid @ParameterObject PageParams pageParams) {
        Page<CustomerResponse> page = customerService.findAllCustomers(pageParams);
        return ControllerUtils.createPageResponseWrapper(page);
    }
}