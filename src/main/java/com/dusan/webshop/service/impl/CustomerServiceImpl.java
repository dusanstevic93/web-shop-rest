package com.dusan.webshop.service.impl;

import com.dusan.webshop.dao.CustomerRepository;
import com.dusan.webshop.dao.UserRepository;
import com.dusan.webshop.dto.request.UpdateCustomerRequest;
import com.dusan.webshop.dto.request.params.PageParams;
import com.dusan.webshop.dto.response.CustomerResponse;
import com.dusan.webshop.entity.Customer;
import com.dusan.webshop.service.CustomerService;
import com.dusan.webshop.service.exception.EmailIsTakenException;
import com.dusan.webshop.service.exception.ResourceNotFoundException;
import com.dusan.webshop.service.exception.UsernameIsTakenException;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class CustomerServiceImpl implements CustomerService {

    private UserRepository userRepository;

    private CustomerRepository customerRepository;


    @Override
    public CustomerResponse findCustomerById(long customerId) {
        Customer customer = findCustomer(customerId);
        return convertEntityToResponse(customer);
    }

    @Override
    public Page<CustomerResponse> findAllCustomers(PageParams pageParams) {
        Pageable pageable = PageRequest.of(pageParams.getPage(), pageParams.getSize());
        Page<Customer> page = customerRepository.findAll(pageable);
        return page.map(this::convertEntityToResponse);
    }

    private CustomerResponse convertEntityToResponse(Customer entity) {
        CustomerResponse response = new CustomerResponse();
        BeanUtils.copyProperties(entity, response);
        BeanUtils.copyProperties(entity.getAddress(), response);
        return response;
    }

    @Override
    public void updateCustomer(long customerId, UpdateCustomerRequest request) {
        Customer customer = findCustomer(customerId);
        // case when customer is updating username
        boolean isUpdatingUsername = !(customer.getUsername().equals(request.getUsername()));
        if (isUpdatingUsername)
            checkIfUsernameIsTaken(request.getUsername());

        // case when customer is updating email
        boolean isUpdatingEmail = !(customer.getEmail().equals(request.getEmail()));
        if (isUpdatingEmail)
            checkIfEmailIsTaken(request.getEmail());

        BeanUtils.copyProperties(request, customer);
        BeanUtils.copyProperties(request, customer.getAddress());

        customerRepository.save(customer);
    }

    private Customer findCustomer(long customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer with id = " + customerId + " does not exist"));
    }

    private void checkIfUsernameIsTaken(String username) {
        boolean isTaken = userRepository.existsByUsername(username);
        if (isTaken)
            throw new UsernameIsTakenException("Username " + username + " is taken");
    }

    private void checkIfEmailIsTaken(String email) {
        boolean isTaken = userRepository.existsByEmail(email);
        if (isTaken)
            throw new EmailIsTakenException("Email " + email + " is taken");
    }
}
