package com.dusan.webshop.dto.request;

import com.dusan.webshop.entity.enums.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class OrderFilterParams {

    private LocalDate creationDateFrom;
    private LocalDate creationDateTo;
    private Integer valueFrom;
    private Integer valueTo;
    private BigDecimal totalWeightFrom;
    private BigDecimal totalWeightTo;
    private OrderStatus status;
}
