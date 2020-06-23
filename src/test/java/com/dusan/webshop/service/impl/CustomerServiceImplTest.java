package com.dusan.webshop.service.impl;

import com.dusan.webshop.dao.CustomerRepository;
import com.dusan.webshop.dao.UserRepository;
import com.dusan.webshop.dao.UserRoleRepository;
import com.dusan.webshop.dto.request.CustomerRegistrationRequest;
import com.dusan.webshop.dto.request.UpdateCustomerRequest;
import com.dusan.webshop.dto.response.CustomerResponse;
import com.dusan.webshop.entity.Address;
import com.dusan.webshop.entity.Customer;
import com.dusan.webshop.service.exception.EmailIsTakenException;
import com.dusan.webshop.service.exception.ResourceNotFoundException;
import com.dusan.webshop.service.exception.UsernameIsTakenException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private UserRoleRepository roleRepository;

    @InjectMocks
    private CustomerServiceImpl userService;

    @Test
    void testCreateCustomerShouldBeSuccessful() {
        // given
        CustomerRegistrationRequest request = getCustomerRegistrationRequestModel();

        // when
        userService.createCustomer(request);

        // then
        ArgumentCaptor<Customer> argumentCaptor = ArgumentCaptor.forClass(Customer.class);
        then(customerRepository).should().save(argumentCaptor.capture());
        Customer savedCustomer = argumentCaptor.getValue();
        assertAll(
                () -> assertEquals(request.getUsername(), savedCustomer.getUsername()),
                () -> assertEquals(request.getPassword(), savedCustomer.getPassword()),
                () -> assertEquals(request.getEmail(), savedCustomer.getEmail()),
                () -> assertEquals(request.getFirstName(), savedCustomer.getFirstName()),
                () -> assertEquals(request.getLastName(), savedCustomer.getLastName()),
                () -> assertEquals(request.getPhone(), savedCustomer.getPhone()),
                () -> assertEquals(request.getStreet(), savedCustomer.getAddress().getStreet()),
                () -> assertEquals(request.getZipCode(), savedCustomer.getAddress().getZipCode()),
                () -> assertEquals(request.getCity(), savedCustomer.getAddress().getCity())
        );
    }

    @Test
    void testCreateCustomerUsernameIsTaken() {
        // given
        CustomerRegistrationRequest request = getCustomerRegistrationRequestModel();
        given(userRepository.existsByUsername(request.getUsername())).willReturn(true);

        // when
        Executable createCustomer = () -> userService.createCustomer(request);

        // then
        assertThrows(UsernameIsTakenException.class, createCustomer);
    }

    @Test
    void testCreateCustomerEmailIsTaken() {
        // given
        CustomerRegistrationRequest request = getCustomerRegistrationRequestModel();
        given(userRepository.existsByEmail(request.getEmail())).willReturn(true);

        // when
        Executable createCustomer = () -> userService.createCustomer(request);

        // then
        assertThrows(EmailIsTakenException.class, createCustomer);
    }

    @Test
    void testFindCustomerByIdShouldBeSuccessful() {
        // given
        long cutomerId = 1L;

        Customer customer = new Customer();
        ReflectionTestUtils.setField(customer, "id", 1L);
        customer.setUsername("Test username");
        customer.setPassword("Test password");
        customer.setEmail("test@mail.com");
        customer.setFirstName("Test first name");
        customer.setLastName("Test last name");
        customer.setPhone("0123456");

        Address address = new Address();
        address.setStreet("Test street 1");
        address.setZipCode("0123");
        address.setCity("Test city");
        customer.setAddress(address);

        given(customerRepository.findById(cutomerId)).willReturn(Optional.of(customer));

        // when
        CustomerResponse response = userService.findCustomerById(cutomerId);

        // then
        assertAll(
                () -> assertEquals(customer.getId(), response.getId()),
                () -> assertEquals(customer.getUsername(), response.getUsername()),
                () -> assertEquals(customer.getPassword(), response.getPassword()),
                () -> assertEquals(customer.getEmail(), response.getEmail()),
                () -> assertEquals(customer.getFirstName(), response.getFirstName()),
                () -> assertEquals(customer.getLastName(), response.getLastName()),
                () -> assertEquals(customer.getPhone(), response.getPhone()),
                () -> assertEquals(customer.getAddress().getStreet(), response.getStreet()),
                () -> assertEquals(customer.getAddress().getZipCode(), response.getZipCode()),
                () -> assertEquals(customer.getAddress().getCity(), response.getCity())
        );
    }

    @Test
    void testFindCustomerByIdCustomerNotFound() {
        // given
        long customerId = 1L;
        given(customerRepository.findById(customerId)).willReturn(Optional.empty());

        // when
        Executable findCustomer = () -> userService.findCustomerById(customerId);

        // then
        assertThrows(ResourceNotFoundException.class, findCustomer);
    }

    @Test
    void testUpdateCustomerShouldBeSuccessful() {
        // given
        long customerId = 1L;

        UpdateCustomerRequest request = new UpdateCustomerRequest();
        request.setUsername("New username");
        request.setEmail("New email");
        request.setFirstName("New first name");
        request.setLastName("New last name");
        request.setPhone("New phone");
        request.setStreet("New street");
        request.setZipCode("New zipcode");
        request.setCity("New city");

        Customer customerToUpdate = new Customer();
        customerToUpdate.setUsername("Old username");
        customerToUpdate.setPassword("Old password");
        customerToUpdate.setEmail("Old email");
        customerToUpdate.setFirstName("Old first name");
        customerToUpdate.setLastName("Old last name");
        customerToUpdate.setPhone("old phone");

        Address address = new Address();
        address.setStreet("Old street");
        address.setZipCode("Old zipcode");
        address.setCity("Old city");
        customerToUpdate.setAddress(address);

        given(customerRepository.findById(customerId)).willReturn(Optional.of(customerToUpdate));

        // when
        userService.updateCustomer(customerId, request);

        // then
        ArgumentCaptor<Customer> argumentCaptor = ArgumentCaptor.forClass(Customer.class);
        then(customerRepository).should().save(argumentCaptor.capture());
        Customer updatedCustomer = argumentCaptor.getValue();
        assertAll(
                () -> assertEquals(request.getUsername(), updatedCustomer.getUsername()),
                () -> assertEquals(request.getEmail(), updatedCustomer.getEmail()),
                () -> assertEquals(request.getFirstName(), updatedCustomer.getFirstName()),
                () -> assertEquals(request.getLastName(), updatedCustomer.getLastName()),
                () -> assertEquals(request.getPhone(), updatedCustomer.getPhone()),
                () -> assertEquals(request.getStreet(), updatedCustomer.getAddress().getStreet()),
                () -> assertEquals(request.getZipCode(), updatedCustomer.getAddress().getZipCode()),
                () -> assertEquals(request.getCity(), updatedCustomer.getAddress().getCity())
        );


    }

    @Test
    void testUpdateCustomerCustomerNotFound() {
        // given
        long customerId = 1L;
        given(customerRepository.findById(customerId)).willReturn(Optional.empty());
        UpdateCustomerRequest request = new UpdateCustomerRequest();

        // when
        Executable updateCustomer = () -> userService.updateCustomer(customerId, request);

        // then
        assertThrows(ResourceNotFoundException.class, updateCustomer);
    }

    @Test
    void testUpdateCustomerUsernameIsTaken() {
        // given
        long customerId = 1L;
        UpdateCustomerRequest request = new UpdateCustomerRequest();
        request.setUsername("Taken username");
        given(userRepository.existsByUsername(request.getUsername())).willReturn(true);

        Customer customerToUpdate = new Customer();
        customerToUpdate.setUsername("Old username");
        given(customerRepository.findById(customerId)).willReturn(Optional.of(customerToUpdate));

        // when
        Executable updateCustomer = () -> userService.updateCustomer(customerId, request);

        // then
        assertThrows(UsernameIsTakenException.class, updateCustomer);
    }

    @Test
    void testUpdateCustomerEmailIsTaken() {
        // given
        long customerId = 1L;
        UpdateCustomerRequest request = new UpdateCustomerRequest();
        request.setUsername("Old username");
        request.setEmail("New email");
        given(userRepository.existsByEmail(request.getEmail())).willReturn(true);

        Customer customerToUpdate = new Customer();
        customerToUpdate.setUsername("Old username");
        customerToUpdate.setEmail("Old email");
        given(customerRepository.findById(customerId)).willReturn(Optional.of(customerToUpdate));

        // when
        Executable updateCustomer = () -> userService.updateCustomer(customerId, request);

        // then
        assertThrows(EmailIsTakenException.class, updateCustomer);
    }

    private CustomerRegistrationRequest getCustomerRegistrationRequestModel() {
        CustomerRegistrationRequest request = new CustomerRegistrationRequest();
        request.setUsername("Test username");
        request.setPassword("Test password");
        request.setEmail("test@mail.com");
        request.setFirstName("Test first name");
        request.setLastName("Test last name");
        request.setPhone("0123456");
        request.setStreet("Test street 1");
        request.setZipCode("0123");
        request.setCity("Test city");

        return request;
    }
}