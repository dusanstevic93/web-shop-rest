package com.dusan.webshop.api.controller;

import com.dusan.webshop.api.docs.Descriptions;
import com.dusan.webshop.dto.request.UpdateCustomerRequest;
import com.dusan.webshop.dto.request.params.PageParams;
import com.dusan.webshop.dto.response.CustomerResponse;
import com.dusan.webshop.dto.response.PageResponseWrapper;
import com.dusan.webshop.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Tag(name = "Customer")
@AllArgsConstructor
@RestController
@RequestMapping("/customers")
public class CustomerController {

    private CustomerService customerService;

    @Operation(summary = "Get an authenticated customer data", description = Descriptions.GET_AUTHENTICATED_CUSTOMER,
                responses = @ApiResponse(responseCode = "200", description = "successful operation"))
    @GetMapping(value = "/me", produces = MediaType.APPLICATION_JSON_VALUE)
    public CustomerResponse getAuthenticatedCustomer(Authentication auth) {
        long userId = (long) auth.getPrincipal();
        return customerService.findCustomerById(userId);
    }

    @Operation(summary = "Update an authenticated customer data", description = Descriptions.UPDATE_AUTHENTICATED_CUSTOMER,
                responses = @ApiResponse(responseCode = "200", description = "successful operation"))
    @PutMapping(value = "/me", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void updateAuthenticatedCustomer(@Valid @RequestBody UpdateCustomerRequest request, Authentication auth) {
        long userId = (long) auth.getPrincipal();
        customerService.updateCustomer(userId, request);
    }

    @Operation(summary = "Get a customer data", description = Descriptions.GET_ANY_CUSTOMER,
                responses = {@ApiResponse(responseCode = "200", description = "successful operation"),
                             @ApiResponse(responseCode = "403", description = "unauthorized access"),
                             @ApiResponse(responseCode = "404", description = "customer is not found")})
    @GetMapping(value = "/{customerId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CustomerResponse getAnyCustomer(@PathVariable long customerId) {
        return customerService.findCustomerById(customerId);
    }


    @Operation(summary = "Get all registered customers", description = Descriptions.GET_ALL_CUSTOMERS,
                responses = {@ApiResponse(responseCode = "200", description = "successful operation"),
                             @ApiResponse(responseCode = "403", description = "unauthorized access")})
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public PageResponseWrapper<CustomerResponse> getAllCustomers(@Valid @ParameterObject PageParams pageParams) {
        Page<CustomerResponse> page = customerService.findAllCustomers(pageParams);
        return ControllerUtils.createPageResponseWrapper(page);
    }
}
