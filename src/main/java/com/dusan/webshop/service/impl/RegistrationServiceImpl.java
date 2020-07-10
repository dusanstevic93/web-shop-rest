package com.dusan.webshop.service.impl;

import com.dusan.webshop.dao.UserRepository;
import com.dusan.webshop.dao.UserRoleRepository;
import com.dusan.webshop.dto.request.CustomerRegistrationRequest;
import com.dusan.webshop.entity.Address;
import com.dusan.webshop.entity.Customer;
import com.dusan.webshop.entity.enums.UserRoles;
import com.dusan.webshop.service.RegistrationService;
import com.dusan.webshop.service.exception.EmailIsTakenException;
import com.dusan.webshop.service.exception.UsernameIsTakenException;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class RegistrationServiceImpl implements RegistrationService {

    private PasswordEncoder passwordEncoder;
    private UserRepository userRepository;
    private UserRoleRepository roleRepository;

    @Override
    public void registerCustomer(CustomerRegistrationRequest request) {
        checkIfUsernameIsTaken(request.getUsername());
        checkIfEmailIsTaken(request.getEmail());

        Customer customer = new Customer();
        customer.setUsername(request.getUsername());
        customer.setPassword(passwordEncoder.encode(request.getPassword()));
        customer.setFirstName(request.getFirstName());
        customer.setLastName(request.getLastName());
        customer.setEmail(request.getEmail());
        customer.setPhone(request.getPhone());

        Address address = new Address();
        address.setStreet(request.getStreet());
        address.setZipCode(request.getZipCode());
        address.setCity(request.getCity());
        customer.setAddress(address);

        customer.setUserRole(roleRepository.getOne(UserRoles.CUSTOMER.getId()));

        userRepository.save(customer);
    }

    private void checkIfUsernameIsTaken(String username) {
        boolean taken = userRepository.existsByUsername(username);
        if (taken)
            throw new UsernameIsTakenException("Username " + username + " is taken");
    }

    private void checkIfEmailIsTaken(String email) {
        boolean taken = userRepository.existsByEmail(email);
        if (taken)
            throw new EmailIsTakenException("Email " + email + " is taken");
    }
}
