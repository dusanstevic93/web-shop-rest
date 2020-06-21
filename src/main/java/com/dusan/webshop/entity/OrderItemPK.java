package com.dusan.webshop.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
public class OrderItemPK implements Serializable {

    private Long orderId;
    private Long productId;
}
