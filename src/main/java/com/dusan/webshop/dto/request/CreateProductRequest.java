package com.dusan.webshop.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
public class CreateProductRequest {

    @NotNull
    private Long categoryId;

    @NotNull
    private Long brandId;

    @NotBlank
    private String name;

    @NotNull
    private BigDecimal price;

    @NotNull
    private Integer quantity;

    @NotNull
    private String shortDescription;

    @NotNull
    private BigDecimal weight;

    @NotNull
    private String longDescription;
}
