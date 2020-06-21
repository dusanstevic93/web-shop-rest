package com.dusan.webshop.dao;

import com.dusan.webshop.entity.Product;
import com.dusan.webshop.entity.ProductBrand;
import com.dusan.webshop.entity.ProductCategory;
import com.dusan.webshop.entity.ProductDetails;
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
//@Transactional(propagation = Propagation.NOT_SUPPORTED)
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
    void testSaveProductDetails() {
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
        product.getImages().add("image1.jpg");
        product.getImages().add("image2.jpg");
        product.setProductBrand(brand);
        product.setProductCategory(category);

        categoryRepository.save(category);
        brandRepository.save(brand);
        product = productRepository.save(product);

        System.out.println("IIIIIIIIIIIIDDDDDDDDD " + product.getId());

        ProductDetails productDetails = new ProductDetails();
        productDetails.setDescription("Some long product description");
        productDetails.setProduct(product);

        productDetailsRepository.save(productDetails);
    }
}