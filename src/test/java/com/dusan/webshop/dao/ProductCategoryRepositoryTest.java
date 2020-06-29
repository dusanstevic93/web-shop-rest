package com.dusan.webshop.dao;

import com.dusan.webshop.entity.ProductCategory;
import org.hibernate.Hibernate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
        productCategory.setName("Category A");

        // when
        ProductCategory savedCategory = repository.saveAndFlush(productCategory);

        // then
        assertNotNull(savedCategory.getId());
    }

    @Test
    @Sql("classpath:scripts/insert-category.sql")
    void testAddSubcategoryToExistingCategory() {
        // given
        ProductCategory parent = repository.findById(1L).get();

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
        assertEquals(parent.getSubCategories().size(), savedParentCategory.getSubCategories().size());
    }

    @Test
    @Sql(value = "classpath:scripts/insert-category-and-subcategories.sql")
    void testFindByIdFetchSubcategories() {
        // given
        /*int expectedNumberOfSubcategories = 2;

        // when
        ProductCategory parentCategory = repository.findByIdFetchSubCategories(1L).get();

        // then
        assertAll(
                () -> assertTrue(Hibernate.isInitialized(parentCategory.getSubCategories())),
                () -> assertEquals(expectedNumberOfSubcategories, parentCategory.getSubCategories().size())
        );*/
    }
}