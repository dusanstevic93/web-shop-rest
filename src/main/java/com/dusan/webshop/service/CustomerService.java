package com.dusan.webshop.service;

import com.dusan.webshop.dto.request.UpdateCustomerRequest;
import com.dusan.webshop.dto.request.params.PageParams;
import com.dusan.webshop.dto.response.CustomerResponse;
import org.springframework.data.domain.Page;

public interface CustomerService {

    CustomerResponse findCustomerById(long customerId);
    Page<CustomerResponse> findAllCustomers(PageParams pageParams);
    void updateCustomer(long customerId, UpdateCustomerRequest request);
}
