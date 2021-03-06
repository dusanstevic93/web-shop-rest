package com.dusan.webshop.dao.repository;

import com.dusan.webshop.entity.Order;
import com.dusan.webshop.entity.OrderItem;
import com.dusan.webshop.entity.OrderItemPK;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, OrderItemPK> {

    Page<OrderItem> findAllByOrder(Order order, Pageable pageable);

    @Query("SELECT oi FROM OrderItem oi WHERE oi.order.id = :orderId")
    List<OrderItem> findAllOrderItemsOfSpecificOrder(@Param("orderId") long orderId);

    @Query("SELECT oi FROM OrderItem oi JOIN oi.order o WHERE o.id = :orderId AND o.customer.id = :customerId")
    List<OrderItem> findAllOrderItemsOfSpecificOrder(@Param("customerId") long customerId, @Param("orderId") long orderId);
}
