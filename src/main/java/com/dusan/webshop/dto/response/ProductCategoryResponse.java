package com.dusan.webshop.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ProductCategoryResponse {

    private Long id;
    private String name;
    private List<ProductCategoryResponse> subcategories = new ArrayList<>();
}
