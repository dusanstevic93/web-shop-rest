package com.dusan.webshop.dao.repository;

import com.dusan.webshop.entity.UserRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRoleRepositoryTest {

    @Autowired
    private UserRoleRepository repository;

    @Test
    void testSaveRole() {
        // given
        UserRole userRole = new UserRole();
        userRole.setName("TEST");

        // when
        UserRole savedRole = repository.saveAndFlush(userRole);

        // then
        assertNotNull(savedRole.getId());
    }
}