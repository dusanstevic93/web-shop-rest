package com.dusan.webshop.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;


@Getter
@Setter
@Entity
public class Product {

    @Setter(AccessLevel.NONE)
    @Version
    private Long version;

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

    @AttributeOverride(name = "imageId", column = @Column(name = "mainImageId"))
    @AttributeOverride(name = "imageUrl", column = @Column(name = "mainImageUrl"))
    private Image mainImage;

    @Setter(AccessLevel.NONE)
    private BigDecimal averageRating;

    @ManyToOne(fetch = FetchType.LAZY)
    private ProductBrand productBrand;

    @ManyToOne(fetch = FetchType.LAZY)
    private ProductCategory productCategory;
}
