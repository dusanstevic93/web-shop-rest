package com.dusan.webshop.entity;

import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class ProductReviewPK implements Serializable {

    private Long customerId;
    private Long productId;
}
