package com.dusan.webshop.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;


@Getter
@Setter
@Entity
public class Product {

    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_id_sequence")
    @SequenceGenerator(name = "product_id_sequence", sequenceName = "product_id_sequence", allocationSize = 1)
    private Long id;

    private String name;

    private BigDecimal price;

    private int quantity;

    private String shortDescription;

    private BigDecimal weight;

    private String mainImage;

    @Setter(AccessLevel.NONE)
    private BigDecimal averageRating;

    @ManyToOne
    private ProductBrand productBrand;

    @ManyToOne(fetch = FetchType.LAZY)
    private ProductCategory productCategory;
}
