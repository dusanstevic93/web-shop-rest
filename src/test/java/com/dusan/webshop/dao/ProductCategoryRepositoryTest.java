package com.dusan.webshop.dao;

import com.dusan.webshop.entity.ProductCategory;
import org.hibernate.Hibernate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
class ProductCategoryRepositoryTest {

    @Autowired
    private ProductCategoryRepository repository;

    @Test
    void testSaveCategory() {
        // given
        ProductCategory productCategory = new ProductCategory();
        productCategory.setName("TEST CATEGORY");

        // when
        ProductCategory savedCategory = repository.saveAndFlush(productCategory);

        // then
        assertNotNull(savedCategory.getId());
    }

    @Test
    @Sql("/scripts/insert-test-data.sql")
    void testAddSubcategoryToExistingCategory() {
        // given
        int expectedNumberOfSubcategories = 2;

        ProductCategory parent = repository.findById(0L).get();

        ProductCategory subcategory1 = new ProductCategory();
        subcategory1.setName("Subcategory 1");

        ProductCategory subcategory2 = new ProductCategory();
        subcategory2.setName("Subcategory 2");

        ProductCategory subcategory3 = new ProductCategory();
        subcategory3.setName("Subcategory 3");

        subcategory2.addSubCategory(subcategory3);

        parent.addSubCategory(subcategory1);
        parent.addSubCategory(subcategory2);

        // when
        ProductCategory savedParentCategory = repository.saveAndFlush(parent);

        // then
        assertEquals(expectedNumberOfSubcategories, savedParentCategory.getSubCategories().size());
    }

    @Test
    @Sql({"/scripts/insert-test-data.sql", "/scripts/insert-categories.sql"})
    void testFindAllCategoriesFetchSubcategories() {
        // given
        int expectedNumberOfSubcategories = 2;

        // when
        List<ProductCategory> categories = repository.findAllCategoriesFetchSubcategories();

        // then
        List<ProductCategory> subcategories = categories.get(0).getSubCategories();
        assertAll(
                () -> assertTrue(Hibernate.isInitialized(subcategories)),
                () -> assertEquals(expectedNumberOfSubcategories, subcategories.size())
        );
    }
}