package com.dusan.webshop.api.controller;

import com.dusan.webshop.api.docs.Descriptions;
import com.dusan.webshop.dto.request.CustomerRegistrationRequest;
import com.dusan.webshop.service.RegistrationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Tag(name = "Registration")
@AllArgsConstructor
@RestController
@RequestMapping("/registration")
public class RegistrationController {

    private RegistrationService registrationService;

    @Operation(summary = "Register a customer", description = Descriptions.REGISTER_CUSTOMER,
                responses = {@ApiResponse(responseCode = "201", description = "successful operation"),
                             @ApiResponse(responseCode = "409", description = "username or email is taken")})
    @PostMapping(value = "/customer", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void registerCustomer(@Valid @RequestBody CustomerRegistrationRequest request) {
        registrationService.registerCustomer(request);
    }
}
