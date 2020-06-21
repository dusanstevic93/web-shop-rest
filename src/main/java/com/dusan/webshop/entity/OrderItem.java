package com.dusan.webshop.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
public class OrderItem {

    @Setter(AccessLevel.NONE)
    @EmbeddedId
    private OrderItemPK id = new OrderItemPK();

    private BigDecimal price;

    private BigDecimal weight;

    private int quantity;

    @MapsId("orderId")
    @ManyToOne(fetch = FetchType.LAZY)
    private Order order;

    @MapsId("productId")
    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;
}
