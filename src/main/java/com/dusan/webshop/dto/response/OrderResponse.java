package com.dusan.webshop.dto.response;

import com.dusan.webshop.entity.enums.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class OrderResponse {

    private Long customerId;
    private Long id;
    private LocalDate creationDate;
    private BigDecimal value;
    private BigDecimal totalWeight;
    private OrderStatus status;
    private String shippingStreet;
    private String shippingZipCode;
    private String shippingCity;
    private String billingStreet;
    private String billingZipCode;
    private String billingCity;
}
