package com.dusan.webshop.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class ProductBrand {

    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_brand_id_sequence")
    @SequenceGenerator(name = "product_brand_id_sequence", sequenceName = "product_brand_id_sequence", allocationSize = 1)
    private Long id;

    private String name;

    @AttributeOverride(name = "imageId", column = @Column(name = "logo_id"))
    @AttributeOverride(name = "imageUrl", column = @Column(name = "logo_url"))
    private Image image;
}
