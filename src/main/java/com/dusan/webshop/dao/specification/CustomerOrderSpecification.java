package com.dusan.webshop.dao.specification;

import com.dusan.webshop.entity.Order;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class CustomerOrderSpecification implements Specification<Order> {

    private long customerId;

    public CustomerOrderSpecification(long customerId) {
        this.customerId = customerId;
    }

    @Override
    public Predicate toPredicate(Root<Order> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.equal(root.get("customer").get("id"), customerId);
    }
}
