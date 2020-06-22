package com.dusan.webshop.dao;

import com.dusan.webshop.entity.Customer;
import com.dusan.webshop.entity.Product;
import com.dusan.webshop.entity.ProductReview;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProductReviewRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProductReviewRepository reviewRepository;


    @Test
    @Sql("classpath:scripts/create-product-review-setup.sql")
    void testSaveProductReview() {
        // given
        Product product = productRepository.getOne(1L);
        Customer customer = customerRepository.getOne(1L);
        ProductReview review = new ProductReview(true);
        review.setCreationDate(LocalDate.now());
        review.setReview("Test review");
        review.setRating(5);
        review.setProduct(product);
        review.setCustomer(customer);

        // when
        ProductReview savedReview = reviewRepository.saveAndFlush(review);

        // then
        assertNotNull(savedReview.getId());
    }
}