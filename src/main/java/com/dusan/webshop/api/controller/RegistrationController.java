package com.dusan.webshop.api.controller;

import com.dusan.webshop.dto.request.CustomerRegistrationRequest;
import com.dusan.webshop.service.RegistrationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping("/registration")
public class RegistrationController {

    private RegistrationService registrationService;

    @PostMapping(value = "/customer", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void registerCustomer(@Valid @RequestBody CustomerRegistrationRequest request) {
        registrationService.registerCustomer(request);
    }
}
