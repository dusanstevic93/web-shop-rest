package com.dusan.webshop.dao;

import com.dusan.webshop.entity.Order;
import com.dusan.webshop.entity.OrderItem;
import com.dusan.webshop.entity.OrderItemPK;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, OrderItemPK> {

    Page<OrderItem> findAllByOrder(Order order, Pageable pageable);
}
