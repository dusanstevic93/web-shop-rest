package com.dusan.webshop.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class CreateProductBrandRequest {

    @Size(min = 1, max = 50)
    @NotBlank
    private String brandName;
}
