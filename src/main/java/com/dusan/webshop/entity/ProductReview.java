package com.dusan.webshop.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Entity
// implement persistable to avoid select statement before inserting a new object
public class ProductReview implements Persistable<ProductReviewPK> {

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @EmbeddedId
    private ProductReviewPK id = new ProductReviewPK();

    private LocalDate creationDate;

    private String review;

    private int rating;

    @MapsId("productId")
    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    @MapsId("customerId")
    @ManyToOne(fetch = FetchType.LAZY)
    private User customer; // not work with child entity - hibernate bug

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @Transient
    private boolean isNew;

    public ProductReview(boolean isNew) {
        this.isNew = isNew;
    }

    @Override
    public ProductReviewPK getId() {
        return id;
    }

    @Override
    public boolean isNew() {
        return isNew;
    }
}
