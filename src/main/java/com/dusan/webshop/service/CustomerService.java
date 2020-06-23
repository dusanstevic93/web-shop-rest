package com.dusan.webshop.service;

import com.dusan.webshop.dto.request.CustomerRegistrationRequest;
import com.dusan.webshop.dto.request.UpdateCustomerRequest;
import com.dusan.webshop.dto.response.CustomerResponse;

public interface CustomerService {

    void createCustomer(CustomerRegistrationRequest request);
    CustomerResponse findCustomerById(long customerId);
    void updateCustomer(long customerId, UpdateCustomerRequest request);
}
