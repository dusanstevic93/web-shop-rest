package com.dusan.webshop.dao.repository;

import com.dusan.webshop.entity.User;
import com.dusan.webshop.dao.projection.AuthenticationProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

    @Query("SELECT " +
            "new com.dusan.webshop.dao.projection.AuthenticationProjection(" +
            "u.id, " +
            "u.username, " +
            "u.password, " +
            "u.userRole.id) " +
            "FROM User u " +
            "WHERE u.username = :username")
    Optional<AuthenticationProjection> getAuthenticationProjection(@Param("username") String username);
}
