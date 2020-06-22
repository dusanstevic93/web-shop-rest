package com.dusan.webshop.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
public class ProductReviewPK implements Serializable {

    private Long productId;
    private Long customerId;
}
