package com.dusan.webshop.service.impl;

import com.dusan.webshop.dao.CustomerRepository;
import com.dusan.webshop.dao.ProductRepository;
import com.dusan.webshop.dao.ProductReviewRepository;
import com.dusan.webshop.dto.request.CreateProductReviewRequest;
import com.dusan.webshop.dto.request.ProductReviewFilterParams;
import com.dusan.webshop.dto.request.ProductReviewPageParams;
import com.dusan.webshop.dto.response.ProductReviewResponse;
import com.dusan.webshop.entity.Customer;
import com.dusan.webshop.entity.Product;
import com.dusan.webshop.entity.ProductReview;
import com.dusan.webshop.entity.ProductReviewPK;
import com.dusan.webshop.entity.specification.ProductReviewSpecification;
import com.dusan.webshop.service.ProductReviewService;
import com.dusan.webshop.service.exception.ConflictException;
import com.dusan.webshop.service.exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@AllArgsConstructor
@Service
public class ProductReviewServiceImpl implements ProductReviewService {

    private CustomerRepository customerRepository;
    private ProductRepository productRepository;
    private ProductReviewRepository reviewRepository;

    @Override
    @Transactional
    public void createProductReview(long customerId, long productId, CreateProductReviewRequest request) {
        checkIfCustomerExists(customerId);
        checkIfProductExists(productId);
        checkIfCustomerAlreadyCreatedReview(customerId, productId);

        Customer customer = customerRepository.getOne(customerId);
        Product product = productRepository.getOne(productId);

        ProductReview review = new ProductReview(true);
        review.setCustomer(customer);
        review.setProduct(product);
        review.setCreationDate(LocalDate.now());
        review.setReview(request.getReview());
        review.setRating(request.getRating());

        reviewRepository.save(review);
    }

    private void checkIfCustomerExists(long customerId) {
        boolean exists = customerRepository.existsById(customerId);
        if (!exists)
            throw new ResourceNotFoundException("Customer with id = " + customerId + " does not exist");
    }

    private void checkIfProductExists(long productId) {
        boolean exists = productRepository.existsById(productId);
        if (!exists)
            throw new ResourceNotFoundException("Product with id = " + productId + " does not exist");
    }

    private void checkIfCustomerAlreadyCreatedReview(long customerId, long productId) {
        ProductReviewPK primaryKey = new ProductReviewPK(customerId, productId);
        boolean exists = reviewRepository.existsById(primaryKey);
        if (exists)
            throw new ConflictException("Customer with id = " + customerId + " already created review for product with id = " + productId);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductReviewResponse> findAllProductReviews(ProductReviewFilterParams filterParams, ProductReviewPageParams pageParams) {
        Specification<ProductReview> specification = new ProductReviewSpecification(filterParams);
        Pageable pageable = getPageable(pageParams);
        Page<ProductReview> page = reviewRepository.findAll(specification, pageable);
        return page.map(this::convertEntityToResponse);
    }

    private Pageable getPageable(ProductReviewPageParams pageParams) {
        Sort sort = null;
        switch (pageParams.getSort()) {
            case DATE: sort = Sort.by(pageParams.getDirection(), "creationDate");
            break;
            case RATING: sort = Sort.by(pageParams.getDirection(), "rating");
            break;
            case UNSORTED: sort = Sort.unsorted();
            break;
        }
        return PageRequest.of(pageParams.getPage(), pageParams.getSize(), sort);
    }

    private ProductReviewResponse convertEntityToResponse(ProductReview productReview) {
        ProductReviewResponse response = new ProductReviewResponse();
        response.setCustomerId(productReview.getCustomer().getId());
        response.setProductId(productReview.getProduct().getId());
        response.setCreationDate(productReview.getCreationDate());
        response.setRating(productReview.getRating());
        response.setReview(productReview.getReview());
        return response;
    }
}
