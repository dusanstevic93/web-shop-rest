package com.dusan.webshop.dao;

import com.dusan.webshop.entity.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;


import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
class ProductDetailsRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductDetailsRepository productDetailsRepository;

    @Autowired
    private ProductCategoryRepository categoryRepository;

    @Autowired
    private ProductBrandRepository brandRepository;


    @Test
    @Sql("classpath:scripts/create-product-setup.sql")
    void testSaveProductDetails() {
        // given
        ProductCategory category = categoryRepository.getOne(1L);
        ProductBrand brand = brandRepository.getOne(1L);

        Product product = new Product();
        product.setName("A10");
        product.setPrice(new BigDecimal(350.00));
        product.setQuantity(15);
        product.setWeight(new BigDecimal(0.2));
        product.setShortDescription("Mobile phone");
        product.setProductBrand(brand);
        product.setProductCategory(category);

        ProductDetails productDetails = new ProductDetails();
        productDetails.setDescription("Some long product description");
        productDetails.setProduct(product);

        // when
        ProductDetails savedProductDetails = productDetailsRepository.saveAndFlush(productDetails);

        // then
        assertNotNull(savedProductDetails.getId());
    }

    @Test
    @Sql("classpath:scripts/insert-product-with-details.sql")
    void testAddProductImage() {
        // given
        ProductDetails productDetails = productDetailsRepository.findById(1L).get();

        Image image1 = new Image("id1", "url1");
        Image image2 = new Image("id2", "url2");

        productDetails.getImages().add(image1);
        productDetails.getImages().add(image2);

        // when
        ProductDetails savedDetails = productDetailsRepository.saveAndFlush(productDetails);

        // then
        assertEquals(2, savedDetails.getImages().size());
    }
}