package com.dusan.webshop.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ProductReviewResponse {

    private long customerId;
    private long productId;
    private LocalDate creationDate;
    private String review;
    private int rating;
}
