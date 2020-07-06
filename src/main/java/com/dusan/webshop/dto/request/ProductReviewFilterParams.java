package com.dusan.webshop.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ProductReviewFilterParams {

    private Long customerId;
    private Long productId;
    private LocalDate creationDateFrom;
    private LocalDate creationDateTo;
    private Integer ratingFrom;
    private Integer ratingTo;
}
