package com.dusan.webshop.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class ProductCategory {

    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_category_id_sequence")
    @SequenceGenerator(name = "product_category_id_sequence", sequenceName = "product_category_id_sequence", allocationSize = 1)
    private Long id;

    private String name;

    private Image image;

    @ManyToOne(fetch = FetchType.LAZY)
    private ProductCategory parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private List<ProductCategory> subCategories = new ArrayList<>();

    public void addSubCategory(ProductCategory productCategory) {
        subCategories.add(productCategory);
        productCategory.setParent(this);
    }
}
