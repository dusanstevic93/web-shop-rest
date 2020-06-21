package com.dusan.webshop.dao;

import com.dusan.webshop.entity.Address;
import com.dusan.webshop.entity.Customer;
import com.dusan.webshop.entity.UserRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional(propagation = Propagation.NOT_SUPPORTED)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository roleRepository;

    @Test
    void testSaveCustomer() {
        Customer customer = new Customer();
        customer.setUsername("asdf");
        customer.setPassword("password");
        customer.setFirstName("Dusan");
        customer.setLastName("Stevic");
        customer.setEmail("dusan@mail.com");
        customer.setPhone("123456");

        Address address = new Address();
        address.setStreet("Street 1");
        address.setZipCode("45613");
        address.setCity("Belgrade");
        customer.setAddress(address);

        UserRole role = new UserRole();
        role.setName("CUSTOMER");

        role = roleRepository.save(role);
        customer.setUserRole(role);

        userRepository.save(customer);

    }
}