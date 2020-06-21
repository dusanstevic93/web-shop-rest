package com.dusan.webshop.dao;

import com.dusan.webshop.entity.Address;
import com.dusan.webshop.entity.Customer;
import com.dusan.webshop.entity.UserRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository roleRepository;

    @Test
    @Sql("classpath:scripts/insert-roles.sql")
    void testSaveCustomer() {
        // given
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

        UserRole role = roleRepository.getOne(2L);
        customer.setUserRole(role);

        // when
        Customer savedCustomer = userRepository.saveAndFlush(customer);

        // then
        assertNotNull(savedCustomer.getId());
    }
}