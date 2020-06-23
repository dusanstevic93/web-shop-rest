package com.dusan.webshop.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

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

    @ManyToOne
    private ProductBrand productBrand;

    @ManyToOne(fetch = FetchType.LAZY)
    private ProductCategory productCategory;
}
