package com.dusan.webshop.service;

import com.dusan.webshop.dto.request.CustomerRegistrationRequest;

public interface RegistrationService {

    void registerCustomer(CustomerRegistrationRequest request);
}
