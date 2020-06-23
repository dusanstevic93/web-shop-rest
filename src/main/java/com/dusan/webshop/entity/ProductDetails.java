package com.dusan.webshop.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


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

    @ElementCollection
    @CollectionTable(name = "product_image", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "image")
    private Set<String> images = new HashSet<>();
}
