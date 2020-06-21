package com.dusan.webshop.dao;

import com.dusan.webshop.entity.Product;
import com.dusan.webshop.entity.ProductBrand;
import com.dusan.webshop.entity.ProductCategory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
@Transactional(propagation = Propagation.NOT_SUPPORTED)
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductCategoryRepository categoryRepository;

    @Autowired
    private ProductBrandRepository brandRepository;


    @Test
    void testSaveProduct() {

        ProductCategory category = new ProductCategory();
        category.setName("Phones");

        ProductBrand brand = new ProductBrand();
        brand.setName("Samsung");

        Product product = new Product();
        product.setName("A10");
        product.setPrice(new BigDecimal(350.00));
        product.setQuantity(15);
        product.setWeight(new BigDecimal(0.2));
        product.setShortDescription("Mobile phone");
        product.setMainImage("image1.jpg");
        product.getImages().add("image1.jpg");
        product.getImages().add("image2.jpg");
        product.setProductBrand(brand);
        product.setProductCategory(category);

        categoryRepository.save(category);
        brandRepository.save(brand);
        productRepository.save(product);

    }
}