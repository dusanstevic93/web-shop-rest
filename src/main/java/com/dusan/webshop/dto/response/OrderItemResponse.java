package com.dusan.webshop.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class OrderItemResponse {

    private Long productId;
    private BigDecimal price;
    private BigDecimal weight;
    private Integer quantity;
}
