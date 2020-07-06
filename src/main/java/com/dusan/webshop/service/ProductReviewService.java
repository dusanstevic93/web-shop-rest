package com.dusan.webshop.service;

import com.dusan.webshop.dto.request.CreateProductReviewRequest;
import com.dusan.webshop.dto.request.ProductReviewFilterParams;
import com.dusan.webshop.dto.request.ProductReviewPageParams;
import com.dusan.webshop.dto.response.ProductReviewResponse;
import org.springframework.data.domain.Page;

public interface ProductReviewService {

    void createProductReview(long customerId, long productId, CreateProductReviewRequest request);
    Page<ProductReviewResponse> findAllProductReviews(ProductReviewFilterParams filterParams, ProductReviewPageParams pageParams);
}
