package com.dusan.webshop.dao;

import com.dusan.webshop.entity.ProductCategory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
@Transactional(propagation = Propagation.NOT_SUPPORTED)
class ProductCategoryRepositoryTest {

    @Autowired
    private ProductCategoryRepository repository;

    @Test
    void testSaveCategory() {
        // given
        ProductCategory productCategory = new ProductCategory();
        productCategory.setName("Category A");

        // when
        ProductCategory savedCategory = repository.save(productCategory);

        // then
        assertAll(
                () -> assertNotNull(savedCategory.getId()),
                () -> assertEquals(productCategory.getName(), savedCategory.getName()),
                () -> assertNull(productCategory.getParent())
        );
    }

    @Test
    void testSaveCategoryWithParent() {
        // given
        ProductCategory category1 = new ProductCategory();
        category1.setName("Category 1");

        ProductCategory category2 = new ProductCategory();
        category2.setName("Category 2");
        category2.setParent(category1);

        // when
        ProductCategory savedCategory1 = repository.save(category1);
        ProductCategory savedCategory2 = repository.save(category2);

        // then
        assertAll(
                () -> assertNotNull(savedCategory2.getParent()),
                () -> assertEquals(savedCategory1.getId(), savedCategory2.getParent().getId())
        );
    }

    @Test
    void testSaveCategoryWithSubcategories() {
        // given
        ProductCategory parent = new ProductCategory();
        parent.setName("parent");

        ProductCategory child1 = new ProductCategory();
        child1.setName("Child1");

        ProductCategory child2 = new ProductCategory();
        child2.setName("child2");

        ProductCategory child3 = new ProductCategory();
        child3.setName("child3");

        child2.addSubCategory(child3);

        parent.addSubCategory(child1);
        parent.addSubCategory(child2);

        // when
        ProductCategory savedParent = repository.save(parent);

        // then
        ProductCategory retrievedParent = repository.findByIdFetchSubCategories(savedParent.getId()).get();
        assertEquals(parent.getSubCategories().size(), retrievedParent.getSubCategories().size());
    }
}