package com.dusan.webshop.dto.request.params;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductFilterParams {

    private BigDecimal priceFrom;
    private BigDecimal priceTo;
    private Integer quantityFrom;
    private Integer quantityTo;
    private BigDecimal weightFrom;
    private BigDecimal weightTo;
    private BigDecimal averageRatingFrom;
    private BigDecimal averageRatingTo;
}
