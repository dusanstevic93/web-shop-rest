package com.dusan.webshop.entity.specification;

import com.dusan.webshop.dto.request.ProductReviewFilterParams;
import com.dusan.webshop.entity.ProductReview;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProductReviewSpecification implements Specification<ProductReview> {

    private Long productId;
    private LocalDate creationDateFrom;
    private LocalDate creationDateTo;
    private Integer ratingFrom;
    private Integer ratingTo;

    public ProductReviewSpecification(ProductReviewFilterParams filterParams) {
        this.productId = filterParams.getProductId();
        this.creationDateFrom = filterParams.getCreationDateFrom();
        this.creationDateTo = filterParams.getCreationDateTo();
        this.ratingFrom = filterParams.getRatingFrom();
        this.ratingTo = filterParams.getRatingTo();
    }

    @Override
    public Predicate toPredicate(Root<ProductReview> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        if (productId != null) {
            Predicate p = criteriaBuilder.equal(root.get("product").get("id"), productId);
            predicates.add(p);
        }

        if (creationDateFrom != null) {
            Predicate p = criteriaBuilder.greaterThanOrEqualTo(root.get("creationDate"), creationDateFrom);
            predicates.add(p);
        }

        if (creationDateTo != null) {
            Predicate p = criteriaBuilder.lessThanOrEqualTo(root.get("creationDate"), creationDateTo);
            predicates.add(p);
        }

        if (ratingFrom != null) {
            Predicate p = criteriaBuilder.greaterThanOrEqualTo(root.get("rating"), ratingFrom);
            predicates.add(p);
        }

        if (ratingTo != null) {
            Predicate p = criteriaBuilder.lessThanOrEqualTo(root.get("rating"), ratingTo);
            predicates.add(p);
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
