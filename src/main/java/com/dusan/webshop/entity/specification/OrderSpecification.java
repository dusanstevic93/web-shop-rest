package com.dusan.webshop.entity.specification;

import com.dusan.webshop.dto.request.OrderFilterParams;
import com.dusan.webshop.entity.Order;
import com.dusan.webshop.entity.enums.OrderStatus;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrderSpecification implements Specification<Order> {

    private LocalDate creationDateFrom;
    private LocalDate creationDateTo;
    private Integer valueFrom;
    private Integer valueTo;
    private BigDecimal totalWeightFrom;
    private BigDecimal totalWeightTo;
    private OrderStatus status;

    public OrderSpecification(OrderFilterParams filterParams) {
        creationDateFrom = filterParams.getCreationDateFrom();
        creationDateTo = filterParams.getCreationDateTo();
        valueFrom = filterParams.getValueFrom();
        valueTo = filterParams.getValueTo();
        totalWeightFrom = filterParams.getTotalWeightFrom();
        totalWeightTo = filterParams.getTotalWeightTo();
        status = filterParams.getStatus();
    }

    @Override
    public Predicate toPredicate(Root<Order> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        if (creationDateFrom != null) {
            Predicate p = criteriaBuilder.greaterThanOrEqualTo(root.get("creationDate"), creationDateFrom);
            predicates.add(p);
        }

        if (creationDateTo != null) {
            Predicate p = criteriaBuilder.lessThanOrEqualTo(root.get("creationDate"), creationDateTo);
            predicates.add(p);
        }

        if (valueFrom != null) {
            Predicate p = criteriaBuilder.greaterThanOrEqualTo(root.get("value"), valueFrom);
            predicates.add(p);
        }

        if (valueTo != null) {
            Predicate p = criteriaBuilder.lessThanOrEqualTo(root.get("value"), valueTo);
            predicates.add(p);
        }

        if (totalWeightFrom != null) {
            Predicate p = criteriaBuilder.greaterThanOrEqualTo(root.get("totalWeight"), totalWeightFrom);
            predicates.add(p);
        }

        if (totalWeightTo != null) {
            Predicate p = criteriaBuilder.lessThanOrEqualTo(root.get("totalWeight"), totalWeightTo);
            predicates.add(p);
        }

        if (status != null) {
            Predicate p = criteriaBuilder.equal(root.get("status"), status);
            predicates.add(p);
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
