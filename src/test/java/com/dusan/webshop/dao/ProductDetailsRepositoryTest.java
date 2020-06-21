package com.dusan.webshop.dao;

import com.dusan.webshop.entity.Product;
import com.dusan.webshop.entity.ProductBrand;
import com.dusan.webshop.entity.ProductCategory;
import com.dusan.webshop.entity.ProductDetails;
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
    @Sql({"classpath:scripts/insert-category.sql", "classpath:scripts/insert-brand.sql"})
    void testSaveProductDetails() {
        ProductCategory category = categoryRepository.getOne(1L);
        ProductBrand brand = brandRepository.getOne(1L);

        Product product = new Product();
        product.setName("A10");
        product.setPrice(new BigDecimal(350.00));
        product.setQuantity(15);
        product.setWeight(new BigDecimal(0.2));
        product.setShortDescription("Mobile phone");
        product.getImages().add("image1.jpg");
        product.getImages().add("image2.jpg");
        product.setProductBrand(brand);
        product.setProductCategory(category);

        ProductDetails productDetails = new ProductDetails();
        productDetails.setDescription("Some long product description");
        productDetails.setProduct(product);

        productDetailsRepository.saveAndFlush(productDetails);
    }
}