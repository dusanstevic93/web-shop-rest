package com.dusan.webshop.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class ProductDetailsResponse extends ProductResponse {

    private String description;
    private String mainImageLink;
    private List<String> linksToAllImages;
}
