package com.dusan.webshop.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CreateProductRequest {

    private Long categoryId;
    private Long brandId;
    private String name;
    private BigDecimal price;
    private Integer quantity;
    private String shortDescription;
    private BigDecimal weight;
    private String longDescription;
}
