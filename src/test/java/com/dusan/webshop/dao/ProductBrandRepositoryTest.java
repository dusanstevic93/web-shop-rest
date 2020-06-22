package com.dusan.webshop.dao;

import com.dusan.webshop.entity.ProductBrand;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
class ProductBrandRepositoryTest {

    @Autowired
    private ProductBrandRepository repository;


    @Test
    void testSaveProductBrand() {
        // given
        ProductBrand brand = new ProductBrand();
        brand.setName("Brand A");

        // when
        ProductBrand savedBrand = repository.saveAndFlush(brand);

        // then
        assertNotNull(savedBrand.getId());
    }
}