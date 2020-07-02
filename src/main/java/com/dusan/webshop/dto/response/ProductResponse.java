package com.dusan.webshop.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductResponse {

    private Long id;
    private Long categoryId;
    private Long brandId;
    private String name;
    private BigDecimal price;
    private int quantity;
    private String shortDescription;
    private BigDecimal weight;
    private BigDecimal averageRating;
    private ImageResponse mainImage;
}
