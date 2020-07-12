package com.dusan.webshop.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CreateProductReviewRequest {

    @NotNull
    private Long productId;

    @NotBlank
    private String review;

    private int rating;
}
