package com.dusan.webshop.dao;

import com.dusan.webshop.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {

    @Query("SELECT c " +
            "FROM ProductCategory c " +
            "JOIN FETCH c.subCategories sc " +
            "WHERE c.id = :categoryId")
    Optional<ProductCategory> findByIdFetchSubCategories(@Param("categoryId") long id);

}
