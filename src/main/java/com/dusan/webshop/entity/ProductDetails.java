package com.dusan.webshop.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Getter
@Setter
@Entity
public class ProductDetails {

    @Setter(AccessLevel.NONE)
    @Id
    private Long id;

    private String description;

    @MapsId
    @OneToOne(cascade = CascadeType.PERSIST)
    private Product product;
}
