package com.dusan.webshop.dao;

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
class UserRoleRepositoryTest {

    @Autowired
    private UserRoleRepository repository;

    @Test
    void testSaveRole() {
        UserRole userRole = new UserRole();
        userRole.setName("CUSTOMER");

        repository.save(userRole);
    }
}