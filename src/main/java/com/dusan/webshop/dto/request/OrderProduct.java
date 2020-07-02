package com.dusan.webshop.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class OrderProduct {

    @NotNull
    private Long version;

    @NotNull
    private Long productId;

    @NotNull
    @Min(1)
    private Integer quantity;
}
