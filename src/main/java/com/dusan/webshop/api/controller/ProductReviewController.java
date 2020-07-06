package com.dusan.webshop.api.controller;

import com.dusan.webshop.dto.request.CreateProductReviewRequest;
import com.dusan.webshop.dto.request.ProductReviewFilterParams;
import com.dusan.webshop.dto.request.ProductReviewPageParams;
import com.dusan.webshop.dto.response.PageResponseWrapper;
import com.dusan.webshop.dto.response.ProductReviewResponse;
import com.dusan.webshop.service.ProductReviewService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Tag(name = "Review")
@AllArgsConstructor
@RestController
public class ProductReviewController {

    private ProductReviewService reviewService;

    @PostMapping(value = "/products/{productId}/reviews", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void createProductReview(@PathVariable long productId, @RequestBody CreateProductReviewRequest request) {
        reviewService.createProductReview(1L, productId, request);
    }

    @GetMapping(value = "/reviews", produces = MediaType.APPLICATION_JSON_VALUE)
    public PageResponseWrapper<ProductReviewResponse> getProductReviews(
            @ParameterObject ProductReviewFilterParams filterParams,
            @Valid @ParameterObject ProductReviewPageParams pageParams) {
        Page<ProductReviewResponse> page = reviewService.findAllProductReviews(filterParams, pageParams);
        return ControllerUtils.createPageResponseWrapper(page);
    }
}
