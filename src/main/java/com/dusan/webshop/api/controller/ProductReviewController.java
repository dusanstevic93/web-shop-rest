package com.dusan.webshop.api.controller;

import com.dusan.webshop.dto.request.CreateProductReviewRequest;
import com.dusan.webshop.dto.request.params.ProductReviewFilterParams;
import com.dusan.webshop.dto.request.params.ProductReviewPageParams;
import com.dusan.webshop.dto.response.PageResponseWrapper;
import com.dusan.webshop.dto.response.ProductReviewResponse;
import com.dusan.webshop.service.ProductReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Tag(name = "Review")
@AllArgsConstructor
@RestController
@RequestMapping("/reviews")
public class ProductReviewController {

    private ProductReviewService reviewService;

    @Operation(summary = "Create a product review", description = "",
                responses = {@ApiResponse(responseCode = "201", description = "successful operation"),
                             @ApiResponse(responseCode = "403", description = "unauthorized access"),
                             @ApiResponse(responseCode = "404", description = "product is not found"),
                             @ApiResponse(responseCode = "409", description = "customer already created review for this product")})
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void createProductReview(@RequestBody CreateProductReviewRequest request, Authentication auth) {
        long customerId = (long) auth.getPrincipal();
        reviewService.createProductReview(customerId, request);
    }

    @Operation(summary = "Get all product reviews", description = "",
                responses = @ApiResponse(responseCode = "200", description = "successful operation"))
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public PageResponseWrapper<ProductReviewResponse> getProductReviews(
            @ParameterObject ProductReviewFilterParams filterParams,
            @Valid @ParameterObject ProductReviewPageParams pageParams) {
        Page<ProductReviewResponse> page = reviewService.findAllProductReviews(filterParams, pageParams);
        return ControllerUtils.createPageResponseWrapper(page);
    }

    @Operation(summary = "Delete a product review", description = "",
                responses = {@ApiResponse(responseCode = "200", description = "successful operation"),
                             @ApiResponse(responseCode = "403", description = "unauthorized access"),
                             @ApiResponse(responseCode = "404", description = "product review is not found")})
    @DeleteMapping(value = "/{customerId}_{productId}")
    public void deleteProductReview(@PathVariable long customerId, @PathVariable long productId) {
        reviewService.deleteProductReview(customerId, productId);
    }
}
