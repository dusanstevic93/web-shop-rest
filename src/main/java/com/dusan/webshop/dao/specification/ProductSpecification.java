package com.dusan.webshop.dao.specification;

import com.dusan.webshop.dto.request.params.ProductFilterParams;
import com.dusan.webshop.entity.Product;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class ProductSpecification implements Specification<Product> {

    private ProductFilterParams filterParams;

    @Override
    public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

        List<Predicate> predicates = new ArrayList<>();

        if (filterParams.getPriceFrom() != null) {
            Predicate predicate = criteriaBuilder.greaterThanOrEqualTo(root.get("price"), filterParams.getPriceFrom());
            predicates.add(predicate);
        }

        if (filterParams.getPriceTo() != null) {
            Predicate predicate = criteriaBuilder.lessThanOrEqualTo(root.get("price"), filterParams.getPriceTo());
            predicates.add(predicate);
        }

        if (filterParams.getAverageRatingFrom() != null) {
            Predicate predicate = criteriaBuilder.greaterThanOrEqualTo(root.get("averageRating"), filterParams.getAverageRatingFrom());
            predicates.add(predicate);
        }

        if (filterParams.getAverageRatingTo() != null) {
            Predicate predicate = criteriaBuilder.lessThanOrEqualTo(root.get("averageRating"), filterParams.getAverageRatingTo());
            predicates.add(predicate);
        }

        if (filterParams.getQuantityFrom() != null) {
            Predicate predicate = criteriaBuilder.greaterThanOrEqualTo(root.get("quantity"), filterParams.getQuantityFrom());
            predicates.add(predicate);
        }

        if (filterParams.getQuantityTo() != null) {
            Predicate predicate = criteriaBuilder.lessThanOrEqualTo(root.get("quantity"), filterParams.getQuantityTo());
            predicates.add(predicate);
        }

        if (filterParams.getWeightFrom() != null) {
            Predicate predicate = criteriaBuilder.greaterThanOrEqualTo(root.get("weight"), filterParams.getWeightFrom());
            predicates.add(predicate);
        }

        if (filterParams.getWeightTo() != null) {
            Predicate predicate = criteriaBuilder.lessThanOrEqualTo(root.get("weight"), filterParams.getWeightTo());
            predicates.add(predicate);
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
