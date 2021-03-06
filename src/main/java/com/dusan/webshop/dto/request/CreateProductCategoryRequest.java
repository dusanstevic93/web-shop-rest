package com.dusan.webshop.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class CreateProductCategoryRequest {

    @NotBlank
    private String name;
}
