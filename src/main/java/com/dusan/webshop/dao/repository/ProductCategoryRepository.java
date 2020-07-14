package com.dusan.webshop.dao.repository;

import com.dusan.webshop.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {

    @Query("SELECT DISTINCT c FROM ProductCategory c LEFT JOIN FETCH c.subCategories")
    List<ProductCategory> findAllCategoriesFetchSubcategories();
}
