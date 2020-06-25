package com.dusan.webshop.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class ProductDetailsResponse {

    private Long id;
    private String brandName;
    private String name;
    private BigDecimal price;
    private int quantity;
    private String shortDescription;
    private BigDecimal weight;
    private double rating;
    private String description;
    private String mainImageLink;
    private List<String> linksToAllImages;
}
